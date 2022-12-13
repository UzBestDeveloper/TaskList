package com.developeruz.tasklist.di.modules

import android.content.Context
import androidx.room.Room
import com.developeruz.tasklist.db.TaskDao
import com.developeruz.tasklist.db.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTaskDao(appDatabase: TaskDatabase): TaskDao {
        return appDatabase.taskDao()
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TaskDatabase {
        return Room.databaseBuilder(
            appContext,
            TaskDatabase::class.java,
            "tasks"
        ).build()
    }
}
