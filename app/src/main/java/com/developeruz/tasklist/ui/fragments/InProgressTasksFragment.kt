package com.developeruz.tasklist.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.developeruz.tasklist.R
import com.developeruz.tasklist.TaskApplication
import com.developeruz.tasklist.adapters.TaskListAdapter
import com.developeruz.tasklist.databinding.FragmentInProgressTasksBinding
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.activities.CreateNewTaskActivity
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModel
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModelFactory
import com.developeruz.tasklist.utils.TaskListener


class InProgressTasksFragment : Fragment(), TaskListener {

    var allTasksViewModel: AllTasksViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentInProgressTasksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_in_progress_tasks, container, false)

        allTasksViewModel = viewModelInit(binding)

        initRecyclerView(binding, allTasksViewModel!!)

        return binding.root
    }

    private fun initRecyclerView(
        binding: FragmentInProgressTasksBinding,
        allTasksViewModel: AllTasksViewModel
    ) {
        val adapter = TaskListAdapter(requireContext(),this)
        binding.recyclerView.adapter = adapter


        allTasksViewModel.inProgressTasks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


    private fun viewModelInit(binding: FragmentInProgressTasksBinding): AllTasksViewModel {
        val viewModelFactory = AllTasksViewModelFactory((requireActivity().application as TaskApplication).repository)
        val allTasksViewModel =
            ViewModelProvider(this, viewModelFactory).get(AllTasksViewModel::class.java)

        binding.allTasksViewModel = allTasksViewModel
        return allTasksViewModel
    }

    override fun onTaskEdited(task: Task) {
        val intent = Intent(requireActivity(), CreateNewTaskActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

    override fun onTaskDeleted(task: Task) {
        allTasksViewModel!!.deleteTask(task)
    }

}