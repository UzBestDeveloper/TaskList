package com.developeruz.tasklist.utils

import com.developeruz.tasklist.db.Task

interface TaskListener {
    fun onTaskEdited(task: Task)
    fun onTaskDeleted(task: Task)
}