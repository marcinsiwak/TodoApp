package pl.msiwak.todoapp.ui.taskList

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.common.observeEvent
import pl.msiwak.todoapp.common.observeFailure
import pl.msiwak.todoapp.databinding.FragmentTaskListBinding
import pl.msiwak.todoapp.util.error.Failure
import timber.log.Timber

class TaskListFragment : Fragment() {

    companion object {
        const val BUNDLE_TASK = "BUNDLE_TASK"
    }

    lateinit var binding: FragmentTaskListBinding

    private val mViewModel by viewModel<TaskListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initListeners()
        initObservers()
        initAdapter()
        mViewModel.onInit()

        return binding.root
    }

    private fun initAdapter() {
        val adapter = TasksAdapter()
        val pagerAdapter = PagerAdapter()

        binding.apply {
            taskListRv.adapter = adapter
            taskListRv.layoutManager = object : LinearLayoutManager(context) {
                override fun scrollVerticallyBy(
                    dx: Int,
                    recycler: Recycler,
                    state: RecyclerView.State
                ): Int {
                    val scrollRange = super.scrollVerticallyBy(dx, recycler, state)
                    val overScroll = dx - scrollRange
                    if (overScroll > 0) {
                        mViewModel.onNewPageLoaded()
                        binding.taskListRv.scrollToPosition(0)

                    }
                    return scrollRange
                }
            }
            taskListPagerRv.adapter = pagerAdapter
            taskListPagerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
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
            TaskListEvents.NavigateToAddTask -> findNavController().navigate(R.id.action_taskListFragment_to_taskFragment)
            is TaskListEvents.NavigateToEditTask -> {
                val bundle = Bundle()
                bundle.putParcelable(BUNDLE_TASK, event.task)
                findNavController().navigate(R.id.action_taskListFragment_to_taskFragment, bundle)
            }
            is TaskListEvents.ShowDeleteQuestion -> showQuestionDialog(event.position)
            is TaskListEvents.ShowTaskDeletedMessage -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun handleError(event: Failure?) {
        when (event) {
            is Failure.GetTaskFailure -> Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT)
                .show()
            is Failure.RemoveTaskFailure -> Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT)
                .show()
            else -> Toast.makeText(context, getString(R.string.error_other), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showQuestionDialog(taskPosition: Int){
        context?.let {
            AlertDialog.Builder(it).setTitle(getString(R.string.remove_task_question))
                .setPositiveButton(getString(R.string.action_yes)) { _: DialogInterface, _: Int ->
                    mViewModel.onItemRemoved(taskPosition)

                }.setNegativeButton(getString(R.string.action_no)) { _: DialogInterface, _: Int ->

                }.show()
        }
    }

}