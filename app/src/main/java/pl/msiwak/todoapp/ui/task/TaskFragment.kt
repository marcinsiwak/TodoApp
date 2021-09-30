package pl.msiwak.todoapp.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.common.observeEvent
import pl.msiwak.todoapp.common.observeFailure
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.databinding.FragmentTaskBinding
import pl.msiwak.todoapp.databinding.FragmentTaskListBinding
import pl.msiwak.todoapp.ui.taskList.TaskListEvents
import pl.msiwak.todoapp.ui.taskList.TaskListFragment.Companion.BUNDLE_TASK
import pl.msiwak.todoapp.ui.taskList.TaskListViewModel
import pl.msiwak.todoapp.util.error.Failure

class TaskFragment : Fragment() {

    lateinit var binding: FragmentTaskBinding

    private val mViewModel by viewModel<TaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val task = arguments?.getParcelable<EditTaskData>(BUNDLE_TASK)

        mViewModel.onInit(task)

        initListeners()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, { handleEvent(it) })
            observeFailure(errorEvent, { handleError(it) })
        }
    }

    private fun initListeners() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,) {
            findNavController().navigate(R.id.action_taskFragment_to_taskListFragment)
        }
    }


    private fun handleEvent(event: TaskEvents?) {
        when(event){
            is TaskEvents.TaskAdded -> {
                findNavController().navigate(R.id.action_taskFragment_to_taskListFragment)
                Toast.makeText(context, event.infoText, Toast.LENGTH_SHORT).show()
            }
            is TaskEvents.TaskEdited -> {
                findNavController().navigate(R.id.action_taskFragment_to_taskListFragment)
                Toast.makeText(context, event.infoText, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun handleError(event: Failure?) {
        when(event){
            is Failure.AddTaskFailure -> Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
            is Failure.UpdateTaskFailure -> Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
            is Failure.EmptyFieldsFailure -> Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, getString(R.string.error_other), Toast.LENGTH_SHORT)
                .show()
        }
    }

}