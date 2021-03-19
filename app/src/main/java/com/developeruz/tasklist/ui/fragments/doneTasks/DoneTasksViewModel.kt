package com.developeruz.tasklist.ui.fragments.doneTasks

import androidx.lifecycle.*
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.fragments.allTasks.TaskRepository
import kotlinx.coroutines.launch


class DoneTasksViewModel(repository: TaskRepository) : ViewModel() {

    private val _tasks = repository.doneTasks.asLiveData()
    val tasks: LiveData<List<Task>>
        get() = _tasks


}