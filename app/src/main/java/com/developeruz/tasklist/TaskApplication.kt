package com.developeruz.tasklist

import android.app.Application
import com.developeruz.tasklist.db.TaskDatabase
import com.developeruz.tasklist.ui.fragments.allTasks.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TaskApplication: Application() {

    private val database by lazy { TaskDatabase.getInstance(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }

}