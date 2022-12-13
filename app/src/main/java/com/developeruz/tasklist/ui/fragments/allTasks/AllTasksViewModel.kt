package com.developeruz.tasklist.ui.fragments.allTasks

import androidx.lifecycle.*
import com.developeruz.tasklist.db.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTasksViewModel @Inject constructor(private var repository: TaskRepository) : ViewModel() {

    private val _tasks = repository.allTasks.asLiveData()
    val tasks: LiveData<List<Task>> get() = _tasks

    private val _doneTasks = repository.doneTasks.asLiveData()
    val doneTasks: LiveData<List<Task>> get() = _doneTasks

    private val _inProgressTasks = repository.inProgressTasks.asLiveData()
    val inProgressTasks: LiveData<List<Task>> get() = _inProgressTasks

    private val _todayTasks = repository.todayTasks.asLiveData()
    val todayTasks: LiveData<List<Task>> get() = _todayTasks


    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch{
        repository.deleteTask(task)
    }



}