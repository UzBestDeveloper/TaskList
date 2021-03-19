package com.developeruz.tasklist.ui.fragments.inProgressTasks

import androidx.lifecycle.*
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.fragments.allTasks.TaskRepository
import kotlinx.coroutines.launch


class InProgressTasksViewModel(repository: TaskRepository) : ViewModel() {

    private val _tasks = repository.inProgressTasks.asLiveData()
    val tasks: LiveData<List<Task>>
        get() = _tasks


}