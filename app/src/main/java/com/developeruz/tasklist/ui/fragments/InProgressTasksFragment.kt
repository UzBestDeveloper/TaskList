package com.developeruz.tasklist.ui.fragments

import android.content.Intent
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developeruz.tasklist.R
import com.developeruz.tasklist.ui.adapters.TaskListAdapter
import com.developeruz.tasklist.databinding.FragmentInProgressTasksBinding
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.activities.CreateNewTaskActivity
import com.developeruz.tasklist.ui.base.BaseFragment
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModel
import com.developeruz.tasklist.utils.TaskListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InProgressTasksFragment : BaseFragment(R.layout.fragment_in_progress_tasks), TaskListener {

    private val binding: FragmentInProgressTasksBinding by viewBinding()
    private val viewModel: AllTasksViewModel by viewModels()
    private var adapter: TaskListAdapter? = null

    override fun setup() {
        adapter = TaskListAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter
    }

    override fun observe() {
        viewModel.inProgressTasks.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    override fun onTaskEdited(task: Task) {
        val intent = Intent(requireActivity(), CreateNewTaskActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
    }

}