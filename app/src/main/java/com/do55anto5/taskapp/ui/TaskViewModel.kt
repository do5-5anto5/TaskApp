package com.do55anto5.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.db.entity.toTaskEntity
import com.do55anto5.taskapp.data.db.repository.TaskRepository
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    private val _taskStateData = MutableLiveData<StateTask>()
    val taskStateData: LiveData<StateTask> = _taskStateData

    private val _taskStateMessage = MutableLiveData<Int>()
    val taskStateMessage: LiveData<Int> = _taskStateMessage

    fun insertOrUpdateTask(id: Long = 0, description: String, status: Status = Status.TODO) {
        if (id == 0L) {
            insertTask(Task(description = description, status = status))
        } else {
            updateTask(Task(id, description, status))
        }

    }

    private fun insertTask(task: Task) = viewModelScope.launch {
        try {

            val id = repository.insertTask(task.toTaskEntity())
            if (id > 0) {
                _taskStateData.postValue(StateTask.Inserted)
                _taskStateMessage.postValue(R.string.dialog_save_success_form_task_fragment)
            }

        } catch (e: Exception) {
            _taskStateMessage.postValue(R.string.error_task_save)
        }
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        try {

            repository.updateTask(task.toTaskEntity())

            _taskStateData.postValue(StateTask.Inserted)
            _taskStateMessage.postValue(R.string.dialog_update_success)

        } catch (e: Exception){
            _taskStateMessage.postValue(R.string.error_task_update)
        }
    }

    fun deleteTask(id: Long) = viewModelScope.launch {
        try {

            repository.deleteTask(id)

            _taskStateData.postValue(StateTask.Deleted)
            _taskStateMessage.postValue(R.string.dialog_delete_success)

        } catch (e: Exception){
            _taskStateMessage.postValue(R.string.error_task_delete)
        }
    }

}

sealed class StateTask {
    object Inserted: StateTask()
    object Updated: StateTask()
    object Deleted: StateTask()
    object Listed: StateTask()
}