package pl.msiwak.todoapp.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.common.observeEvent
import pl.msiwak.todoapp.common.observeFailure
import pl.msiwak.todoapp.databinding.FragmentTaskBinding
import pl.msiwak.todoapp.databinding.FragmentTaskListBinding
import pl.msiwak.todoapp.ui.taskList.TaskListEvents
import pl.msiwak.todoapp.ui.taskList.TaskListViewModel
import pl.msiwak.todoapp.util.error.Failure

class TaskFragment : Fragment() {

    lateinit var binding: FragmentTaskBinding

    private val mViewModel by viewModel<TaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        initListeners()

        return binding.root
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, { handleEvent(it) })
            observeFailure(errorEvent, { handleError(it) })
        }
    }

    private fun initListeners() {

    }


    private fun handleEvent(event: TaskListEvents?) {
        when(event){

        }
    }
    private fun handleError(event: Failure?) {
        when(event){

        }
    }

}