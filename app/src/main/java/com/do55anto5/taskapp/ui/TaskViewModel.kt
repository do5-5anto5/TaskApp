package com.do55anto5.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.do55anto5.taskapp.data.db.repository.TaskRepository
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.util.StateView

class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    private val _taskStateData = MutableLiveData<TaskState>()
    val taskStateData: LiveData<TaskState> = _taskStateData

    private val _taskStateMessage = MutableLiveData<Int>()
    val taskStateMessage: LiveData<Int> = _taskStateMessage

    private val _taskList = MutableLiveData<StateView<List<Task>>>()
    val taskList: LiveData<StateView<List<Task>>> = _taskList

    private val _taskInsert = MutableLiveData<StateView<Task>>()
    val taskInsert: LiveData<StateView<Task>> = _taskInsert

    private val _taskUpdate = MutableLiveData<StateView<Task>>()
    val taskUpdate: LiveData<StateView<Task>> = _taskUpdate

    private val _taskDelete = MutableLiveData<StateView<Task>>()
    val taskDelete: LiveData<StateView<Task>> = _taskDelete

    fun insertOrUpdateTask(id: Long = 0, description: String, status: Status = Status.TODO) {
        if (id == 0L) {
            insertTask(Task(description = description, status = status))
        } else {
            updateTask(Task(id, description, status))
        }

    }

    fun getTasks(){

    }

    private fun insertTask(task: Task) {

    }

    private fun updateTask(task: Task) {

    }

    fun deleteTask(task: Task){

    }

}

sealed class TaskState {
    object Inserted: TaskState()
    object Updated: TaskState()
    object Deleted: TaskState()
    object Listed: TaskState()
}