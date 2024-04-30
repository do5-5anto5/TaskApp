package com.do55anto5.taskapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.do55anto5.taskapp.data.db.dao.TaskDao
import com.do55anto5.taskapp.data.db.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun tasDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}