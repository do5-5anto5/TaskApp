package com.do55anto5.taskapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.do55anto5.taskapp.data.db.entity.TaskEntity
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task

@Dao
interface TaskDao {

    @Query("SELECT *  FROM task_table ORDER BY id DESC")
    suspend fun getAllTask(): List<Task>

    @Insert(onConflict = IGNORE)
    suspend fun insertTask(taskEntity: TaskEntity): Long

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("UPDATE task_table SET description = :description, status = :status WHERE id = :id")
    suspend fun updateTask(
        id:Long,
        description: String,
        status: Status
    )
}