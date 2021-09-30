package pl.msiwak.todoapp.ui.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.BaseTest
import pl.msiwak.todoapp.util.error.Failure
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import pl.msiwak.todoapp.util.helpers.ResourceProvider

@ExperimentalCoroutinesApi
class TaskViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var resProvider: ResourceProvider

    private lateinit var viewModel: TaskViewModel

    private val mockEditTaskData =
        EditTaskData(1, Task("Title", "Description", "url", "28-Sep-2021"))

    override fun setup() {
        super.setup()
        viewModel = TaskViewModel(firebaseDatabase, resProvider)
        Dispatchers.setMain(testDispatcher)
    }

    override fun tearDown() {
        super.tearDown()
        verifyNoMoreInteractions(firebaseDatabase, resProvider)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun onInit_edit_task() {
        `when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        viewModel.onInit(mockEditTaskData)

        assertEquals(MOCKED_STRING, viewModel.fragmentTitle.value)
        assertEquals(mockEditTaskData.task?.title, viewModel.title.value)
        assertEquals(mockEditTaskData.task?.iconUrl, viewModel.iconUrl.value)
        assertEquals(mockEditTaskData.task?.description, viewModel.description.value)
        assertEquals(true, viewModel.isEditMode.value)
        verify(resProvider, times(2)).getString(anyInt())
    }

    @Test
    fun onInit_add_task() {
        `when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        viewModel.onInit(null)

        assertEquals(MOCKED_STRING, viewModel.fragmentTitle.value)
        assertEquals(false, viewModel.isEditMode.value)
        verify(resProvider, times(2)).getString(anyInt())
    }

    @Test
    fun onAddClicked_success() = runBlockingTest {
        `when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        viewModel.title.value = MOCKED_STRING
        viewModel.description.value = MOCKED_STRING
        mockAddTaskSuccess()
        viewModel.onAddClicked()

        assertEquals(
            MOCKED_STRING,
            (viewModel.event.value?.peekContent() as TaskEvents.TaskAdded).infoText
        )
        verify(firebaseDatabase, times(1)).addTask(any(), any(), any())
        verify(resProvider, times(2)).getString(anyInt())
    }

    @Test
    fun onAddClicked_failure() = runBlockingTest {
        `when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        viewModel.title.value = MOCKED_STRING
        viewModel.description.value = MOCKED_STRING
        mockAddTaskError()
        viewModel.onAddClicked()

        assertEquals(
            MOCKED_STRING,
            (viewModel.errorEvent.value?.peekContent() as Failure.AddTaskFailure).errorText
        )
        verify(firebaseDatabase, times(1)).addTask(any(), any(), any())
        verify(resProvider, times(2)).getString(anyInt())
    }

    @Test
    fun onUpdateClicked_success() = runBlockingTest {
        `when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        mockUpdateTaskSuccess()

        viewModel.onInit(mockEditTaskData)
        viewModel.onUpdateClicked()

        assertEquals(MOCKED_STRING, viewModel.fragmentTitle.value)
        assertEquals(mockEditTaskData.task?.title, viewModel.title.value)
        assertEquals(mockEditTaskData.task?.iconUrl, viewModel.iconUrl.value)
        assertEquals(mockEditTaskData.task?.description, viewModel.description.value)
        assertEquals(true, viewModel.isEditMode.value)
        assertEquals(
            MOCKED_STRING,
            (viewModel.event.value?.peekContent() as TaskEvents.TaskEdited).infoText
        )
        verify(resProvider, times(3)).getString(anyInt())
        verify(firebaseDatabase, times(1)).editTask(anyInt(), any(), any(), any())
    }

    @Test
    fun onUpdateClicked_error() = runBlockingTest {
        `when`(resProvider.getString(anyInt())).thenReturn(MOCKED_STRING)
        mockUpdateTaskFailure()

        viewModel.onInit(mockEditTaskData)
        viewModel.onUpdateClicked()

        assertEquals(MOCKED_STRING, viewModel.fragmentTitle.value)
        assertEquals(mockEditTaskData.task?.title, viewModel.title.value)
        assertEquals(mockEditTaskData.task?.iconUrl, viewModel.iconUrl.value)
        assertEquals(mockEditTaskData.task?.description, viewModel.description.value)
        assertEquals(true, viewModel.isEditMode.value)
        assertEquals(
            MOCKED_STRING,
            (viewModel.errorEvent.value?.peekContent() as Failure.UpdateTaskFailure).errorText
        )
        verify(resProvider, times(3)).getString(anyInt())
        verify(firebaseDatabase, times(1)).editTask(anyInt(), any(), any(), any())
    }

    private suspend fun mockAddTaskSuccess() {
        doAnswer {
            val callback = it.arguments[1] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).addTask(any(), any(), any())
    }

    private suspend fun mockAddTaskError() {
        doAnswer {
            val callback = it.arguments[2] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).addTask(any(), any(), any())
    }

    private suspend fun mockUpdateTaskSuccess() {
        doAnswer {
            val callback = it.arguments[2] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).editTask(anyInt(), any(), any(), any())
    }

    private suspend fun mockUpdateTaskFailure() {
        doAnswer {
            val callback = it.arguments[3] as () -> Unit
            callback.invoke()
            return@doAnswer Unit
        }.`when`(firebaseDatabase).editTask(anyInt(), any(), any(), any())
    }


}