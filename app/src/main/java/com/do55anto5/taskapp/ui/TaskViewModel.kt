package com.do55anto5.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.util.FirebaseHelper

class TaskViewModel: ViewModel() {

    private val _taskInsert = MutableLiveData<Task>()
    val taskInsert: LiveData<Task> = _taskInsert

    private val _taskUpdate = MutableLiveData<Task>()
    val taskUpdate: LiveData<Task> = _taskUpdate

    fun setUpdateTask(task: Task) {
        _taskUpdate.postValue(task)
    }

    fun taskInsert(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserId())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    _taskInsert.postValue(task)
                }
            }
    }
}