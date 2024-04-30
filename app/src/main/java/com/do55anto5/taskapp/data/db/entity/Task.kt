package com.do55anto5.taskapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.do55anto5.taskapp.data.model.Status

@Entity("task_table")
class Task(
    @PrimaryKey(true)
    val id : Long = 0,

    val description: String,

    val status: Status
)