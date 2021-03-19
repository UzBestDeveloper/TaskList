package com.developeruz.tasklist.ui.fragments.allTasks

import androidx.lifecycle.*
import com.developeruz.tasklist.db.Task
import kotlinx.coroutines.launch


class AllTasksViewModel(private var repository: TaskRepository) : ViewModel() {

    private val _tasks = repository.allTasks.asLiveData()
    val tasks: LiveData<List<Task>>
        get() = _tasks


    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }


}