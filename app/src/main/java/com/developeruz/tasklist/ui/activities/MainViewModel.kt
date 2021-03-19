package com.developeruz.tasklist.ui.activities

import androidx.lifecycle.*
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.fragments.allTasks.TaskRepository
import kotlinx.coroutines.launch


class MainViewModel(private var repository: TaskRepository) : ViewModel() {

    private val _tasks = repository.allTasks.asLiveData()
    val tasks: LiveData<List<Task>>
        get() = _tasks

    private val _inProgressTasks = repository.inProgressTasks.asLiveData()
    val inProgressTasks: LiveData<List<Task>>
        get() = _inProgressTasks

    private val _doneTasks = repository.doneTasks.asLiveData()
    val doneTasks: LiveData<List<Task>>
        get() = _doneTasks

    private val _todayTasks = repository.todayTasks.asLiveData()
    val todayTasks: LiveData<List<Task>>
        get() = _todayTasks

}