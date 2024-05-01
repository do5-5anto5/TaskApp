package com.do55anto5.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.do55anto5.taskapp.data.db.repository.TaskRepository
import com.do55anto5.taskapp.data.model.Task
import kotlinx.coroutines.launch

class TaskListViewModel(private val repository: TaskRepository): ViewModel() {

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    fun getAllTasks() = viewModelScope.launch {
        _taskList.postValue(repository.getAllTask())
    }
}