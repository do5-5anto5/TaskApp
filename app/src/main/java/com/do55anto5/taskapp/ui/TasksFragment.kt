package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.db.AppDatabase
import com.do55anto5.taskapp.data.db.repository.TaskRepository
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentTasksBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter


class TasksFragment : Fragment() {

    private var _bind: FragmentTasksBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskListViewModel by viewModels {
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {

                    val database = AppDatabase.getDatabase(requireContext())

                    val repository = TaskRepository(database.taskDao())

                    @Suppress("UNCHECKED_CAST")
                    return TaskListViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bind = FragmentTasksBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

        initRecyclerView()

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTasks()
    }

    private fun initListener() {
        bind.fabAdd.setOnClickListener {
            val action = TasksFragmentDirections
                .actionTasksFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            taskAdapter.submitList(taskList)
            listEmpty(taskList)
        }
    }

    private fun initRecyclerView() {
        taskAdapter = TaskAdapter { task, option ->
            selectedOption(task, option)
        }

        with(bind.rvTasks){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun selectedOption(task: Task, option: Int) {
//        when (option) {
//            TaskAdapter.SELECT_REMOVE -> {
//                showBottomSheet(
//                    titleDialog = R.string.text_title_dialog_delete,
//                    titleButton = R.string.text_dialog_button_confirm,
//                    message = getString(R.string.text_message_dialog_delete),
//                    onClick = {
//                        viewModel.deleteTask(task)
//                    }
//                )
//            }
//
//            TaskAdapter.SELECT_EDIT -> {
//                val action = TasksFragmentDirections
//                    .actionTasksFragmentToFormTaskFragment(task)
//                findNavController().navigate(action)
//            }
//
//            TaskAdapter.SELECT_DETAILS -> {
//                Toast.makeText(
//                    requireContext(), "Detalhes: ${task.description}", Toast.LENGTH_SHORT
//                )
//                    .show()
//            }
//        }
    }

    private fun listEmpty(taskList: List<Task>){
        bind.textInfo.text = if(taskList.isEmpty()){
            getString(R.string.text_task_list_empty)
        } else {
            ""
        }
        bind.progressBar.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}