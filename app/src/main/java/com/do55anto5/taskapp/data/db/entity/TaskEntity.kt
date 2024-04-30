package com.do55anto5.taskapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task

@Entity("task_table")
class TaskEntity(
    @PrimaryKey(true)
    val id : Long = 0,

    val description: String,

    val status: Status
)

fun Task.toTaskEntity(): TaskEntity {
    return with(this){
        TaskEntity(
            id = this.id,
            description = this.description,
            status = this.status
        )
    }
}