package com.developeruz.tasklist.ui.fragments.inProgressTasks

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
import com.developeruz.tasklist.databinding.FragmentDoneTasksBinding
import com.developeruz.tasklist.databinding.FragmentInProgressTasksBinding
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.fragments.doneTasks.DoneTasksViewModel
import com.developeruz.tasklist.ui.fragments.doneTasks.DoneTasksViewModelFactory


class InProgressTasksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentInProgressTasksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_in_progress_tasks, container, false)

        val inProgressTasksViewModel = viewModelInit(binding)

        initRecyclerView(binding, inProgressTasksViewModel)

        return binding.root
    }

    private fun initRecyclerView(
        binding: FragmentInProgressTasksBinding,
        inProgressTasksViewModel: InProgressTasksViewModel
    ) {
        val adapter = TaskListAdapter(requireContext(), TaskClickListener { navigateToEdit(it) })
        binding.recyclerView.adapter = adapter


        inProgressTasksViewModel.tasks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun navigateToEdit(it: Task) {

    }


    private fun viewModelInit(binding: FragmentInProgressTasksBinding): InProgressTasksViewModel {
        val viewModelFactory = InProgressTasksViewModelFactory((requireActivity().application as TaskApplication).repository)
        val inProgressTasksViewModel =
            ViewModelProvider(this, viewModelFactory).get(InProgressTasksViewModel::class.java)

        binding.inProgressTasksViewModel = inProgressTasksViewModel
        return inProgressTasksViewModel
    }

}