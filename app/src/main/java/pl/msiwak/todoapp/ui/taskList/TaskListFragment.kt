package pl.msiwak.todoapp.ui.taskList

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
import pl.msiwak.todoapp.databinding.FragmentTaskListBinding
import pl.msiwak.todoapp.util.error.Failure

class TaskListFragment : Fragment() {

    lateinit var binding: FragmentTaskListBinding

    private val mViewModel by viewModel<TaskListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        val adapter = TasksAdapter()
        adapter.setData(listOf("task1", "task2"))
        binding.taskListRv.adapter = adapter
    }

    private fun initObservers() {
        mViewModel.apply {
            observeEvent(event, { handleEvent(it) })
            observeFailure(errorEvent, { handleError(it) })
        }
    }

    private fun initListeners() {
        binding.taskListBtn.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskFragment)
        }
    }


    private fun handleEvent(event: TaskListEvents?) {
        when (event) {

        }
    }

    private fun handleError(event: Failure?) {
        when (event) {

        }
    }

}