package com.developeruz.tasklist.ui.fragments.doneTasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.developeruz.tasklist.R
import com.developeruz.tasklist.TaskApplication
import com.developeruz.tasklist.adapters.TaskClickListener
import com.developeruz.tasklist.adapters.TaskListAdapter
import com.developeruz.tasklist.databinding.FragmentAllTasksBinding
import com.developeruz.tasklist.databinding.FragmentDoneTasksBinding
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModel
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModelFactory


class DoneTasksFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentDoneTasksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_done_tasks, container, false)

        val doneTasksViewModel = viewModelInit(binding)

        initRecyclerView(binding, doneTasksViewModel)

        return binding.root
    }

    private fun initRecyclerView(
        binding: FragmentDoneTasksBinding,
        doneTasksViewModel: DoneTasksViewModel
    ) {
        val adapter = TaskListAdapter(requireContext(), TaskClickListener { navigateToEdit(it) })
        binding.recyclerView.adapter = adapter


        doneTasksViewModel.tasks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun navigateToEdit(it: Task) {

    }


    private fun viewModelInit(binding: FragmentDoneTasksBinding): DoneTasksViewModel {
        val viewModelFactory = DoneTasksViewModelFactory((requireActivity().application as TaskApplication).repository)
        val doneTasksViewModel =
            ViewModelProvider(this, viewModelFactory).get(DoneTasksViewModel::class.java)

        binding.doneTasksViewModel = doneTasksViewModel
        return doneTasksViewModel
    }
}