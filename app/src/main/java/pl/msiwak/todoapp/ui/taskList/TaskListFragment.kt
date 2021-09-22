package pl.msiwak.todoapp.ui.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.taskListBtn.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskFragment)
        }
    }

}