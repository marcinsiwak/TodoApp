package pl.msiwak.todoapp.ui.taskList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.BaseTest
import pl.msiwak.todoapp.util.error.Failure
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import pl.msiwak.todoapp.util.helpers.ResourceProvider

@ExperimentalCoroutinesApi
class TaskListViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var resProvider: ResourceProvider

    private lateinit var viewModel: TaskListViewModel

    private val mockedTask = Task("Title", "Description", "url", "28-Sep-2021")

    private val mockedTaskList = listOf(mockedTask, mockedTask, mockedTask)

    private val mockedPosition = 1

    private val mockedTasksToDisplay = mockedTaskList.filterIndexed { index, _ ->
        index >= 0 + 30 * mockedPosition && index <= 29 + 30 * mockedPosition }

    override fun setup() {
        super.setup()
        viewModel = TaskListViewModel(firebaseDatabase, resProvider)
        Dispatchers.setMain(testDispatcher)
    }

    override fun tearDown() {
        super.tearDown()
        Mockito.verifyNoMoreInteractions(firebaseDatabase, resProvider)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun onInit_success() = runBlockingTest {
        mockGetTaskSuccess()

        viewModel.onInit()

        assertEquals(false, viewModel.isLoaderVisible.value)
        verify(firebaseDatabase, times(1)).getTasks(any(), any())
    }

    @Test
    fun onInit_error() = runBlockingTest {
        Mockito.`when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        mockGetTaskError()

        viewModel.onInit()

        assertEquals(
            MOCKED_STRING,
            (viewModel.errorEvent.value?.peekContent() as Failure.GetTaskFailure).errorText
        )
        assertEquals(false, viewModel.isLoaderVisible.value)
        verify(resProvider, times(1)).getString(anyInt())
        verify(firebaseDatabase, times(1)).getTasks(any(), any())
    }

    @Test
    fun onAddTaskClicked() {
        viewModel.onAddTaskClicked()

        assertEquals(
            TaskListEvents.NavigateToAddTask,
            (viewModel.event.value?.peekContent())
        )
    }

    @Test
    fun onEditTaskClicked() = runBlockingTest{
        mockGetTaskSuccess()

        viewModel.onInit()
        viewModel.onEditTaskClicked(mockedPosition)

        verify(firebaseDatabase, times(1)).getTasks(any(), any())
        assertEquals(false, viewModel.isLoaderVisible.value)
        assertEquals(
            mockedPosition,
            (viewModel.event.value?.peekContent() as TaskListEvents.NavigateToEditTask).task?.position
        )
        assertEquals(
            mockedTaskList[mockedPosition],
            (viewModel.event.value?.peekContent() as TaskListEvents.NavigateToEditTask).task?.task
        )
    }

    @Test
    fun onItemChosenToRemove() {
        viewModel.onItemChosenToRemove(mockedPosition)

        assertEquals(
            1,
            (viewModel.event.value?.peekContent() as TaskListEvents.ShowDeleteQuestion).position
        )
    }

    @Test
    fun onItemRemoved_success() = runBlockingTest {
        Mockito.`when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        mockDeleteTaskSuccess()

        viewModel.onItemRemoved(mockedPosition)

        verify(resProvider, times(1)).getString(anyInt())
        verify(firebaseDatabase, times(1)).deleteTask(anyInt(), any(), any())
        assertEquals(
            MOCKED_STRING,
            (viewModel.event.value?.peekContent() as TaskListEvents.ShowTaskDeletedMessage).message
        )
    }

    @Test
    fun onItemRemoved_error() = runBlockingTest {
        Mockito.`when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        mockDeleteTaskError()
        viewModel.onItemRemoved(mockedPosition)

        verify(firebaseDatabase, times(1)).deleteTask(anyInt(), any(), any())
        verify(resProvider, times(1)).getString(anyInt())
        assertEquals(
            MOCKED_STRING,
            (viewModel.errorEvent.value?.peekContent() as Failure.RemoveTaskFailure).errorText
        )
    }

    @Test
    fun onPageClicked() = runBlockingTest{
        mockGetTaskSuccess()
        viewModel.onInit()
        viewModel.onPageClicked(mockedPosition)

        assertEquals(false, viewModel.isLoaderVisible.value)
        assertEquals(mockedTasksToDisplay, viewModel.tasksToDisplayList.value)
        verify(firebaseDatabase, times(1)).getTasks(any(), any())
    }

    @Test
    fun onNewPageLoaded() = runBlockingTest{
        mockGetTaskSuccess()
        viewModel.onInit()
        viewModel.onNewPageLoaded()

        verify(firebaseDatabase, times(1)).getTasks(any(), any())
    }

    private suspend fun mockGetTaskSuccess() {
        Mockito.doAnswer {
            val callback = it.arguments[0] as (List<Task>) -> Unit
            callback.invoke(mockedTaskList)
            return@doAnswer Unit
        }.`when`(firebaseDatabase).getTasks(any(), any())
    }

    private suspend fun mockDeleteTaskSuccess() {
        Mockito.doAnswer {
            val callback = it.arguments[1] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).deleteTask(anyInt(), any(), any())
    }

    private suspend fun mockGetTaskError() {
        Mockito.doAnswer {
            val callback = it.arguments[1] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).getTasks(any(), any())
    }

    private suspend fun mockDeleteTaskError() {
        Mockito.doAnswer {
            val callback = it.arguments[2] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).deleteTask(anyInt(), any(), any())
    }

}