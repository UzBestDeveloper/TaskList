package com.developeruz.tasklist.ui.fragments.allTasks

import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.db.TaskDao
import com.developeruz.tasklist.utils.today
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    val doneTasks: Flow<List<Task>> = taskDao.getDoneTasks()

    val inProgressTasks: Flow<List<Task>> = taskDao.getInProgressTasks()

    val todayTasks: Flow<List<Task>> = taskDao.getTodayTasks(today())

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

}