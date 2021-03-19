package com.developeruz.tasklist.ui.fragments.allTasks

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
import com.developeruz.tasklist.db.Task


class AllTasksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentAllTasksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_tasks, container, false)

        val allTasksViewModel = viewModelInit(binding)

        initRecyclerView(binding, allTasksViewModel)

        return binding.root
    }

    private fun initRecyclerView(
        binding: FragmentAllTasksBinding,
        allTasksViewModel: AllTasksViewModel
    ) {
        val adapter = TaskListAdapter(requireContext(),TaskClickListener { navigateToEdit(it) })
        binding.recyclerView.adapter = adapter


        allTasksViewModel.tasks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun navigateToEdit(it: Task) {

    }


    private fun viewModelInit(binding: FragmentAllTasksBinding): AllTasksViewModel {
        val viewModelFactory = AllTasksViewModelFactory((requireActivity().application as TaskApplication).repository)
        val allTasksViewModel =
            ViewModelProvider(this, viewModelFactory).get(AllTasksViewModel::class.java)

        binding.allTasksViewModel = allTasksViewModel
        return allTasksViewModel
    }

}