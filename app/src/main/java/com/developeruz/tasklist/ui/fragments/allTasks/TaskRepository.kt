package com.developeruz.tasklist.ui.fragments.allTasks

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.db.TaskDao
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    val doneTasks: Flow<List<Task>> = taskDao.getDoneTasks()

    val inProgressTasks: Flow<List<Task>> = taskDao.getInProgressTasks()


    @SuppressLint("SimpleDateFormat")
    val today: String = SimpleDateFormat("dd/MM/yyyy").format(Date(Calendar.getInstance().time.time))
    val todayTasks: Flow<List<Task>> = taskDao.getTodayTasks(today)


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