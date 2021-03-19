package com.developeruz.tasklist.ui.fragments.doneTasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developeruz.tasklist.ui.fragments.allTasks.TaskRepository

@Suppress("UNCHECKED_CAST")
class DoneTasksViewModelFactory(private var repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoneTasksViewModel::class.java)) {
            return DoneTasksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}