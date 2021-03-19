package com.developeruz.tasklist.ui.fragments.inProgressTasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developeruz.tasklist.ui.fragments.allTasks.TaskRepository

@Suppress("UNCHECKED_CAST")
class InProgressTasksViewModelFactory(private var repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InProgressTasksViewModel::class.java)) {
            return InProgressTasksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}