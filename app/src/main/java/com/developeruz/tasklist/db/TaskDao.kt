package com.developeruz.tasklist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Query("SELECT * from tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE status = 1")
    fun getDoneTasks(): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE status = 0")
    fun getInProgressTasks(): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE due_date LIKE :today AND status = 0 ")
    fun getTodayTasks(today:String): Flow<List<Task>>

}