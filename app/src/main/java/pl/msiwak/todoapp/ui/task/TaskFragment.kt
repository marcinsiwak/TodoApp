package pl.msiwak.todoapp.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.msiwak.todoapp.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        initListeners()

        return binding.root
    }

    private fun initListeners() {

    }

}