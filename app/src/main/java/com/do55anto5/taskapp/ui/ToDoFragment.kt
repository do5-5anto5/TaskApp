package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentToDoBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter
import com.do55anto5.taskapp.util.FirebaseHelper
import com.do55anto5.taskapp.util.showBottomSheet


class ToDoFragment : Fragment() {

    private var _bind: FragmentToDoBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bind = FragmentToDoBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

        initRecyclerView()

        observeViewModel()

        viewModel.getTasks(Status.TODO)
    }

    private fun initListener() {
        bind.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            bind.progressBar.isVisible = false
            listEmpty(taskList)

            taskAdapter.submitList(taskList)
        }

        viewModel.taskInsert.observe(viewLifecycleOwner) { task ->
            if (task.status == Status.TODO) {

                val adapterCurrentList = taskAdapter.currentList

                val newListWithUpdatedTask = adapterCurrentList.toMutableList().apply {
                    add(0, task)
                }

                taskAdapter.submitList(newListWithUpdatedTask)

                setPositionRecyclerView()
            }
        }

        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->

            val adapterCurrentList = taskAdapter.currentList

            val newListWithUpdatedTask = adapterCurrentList.toMutableList().apply {
               if (updateTask.status == Status.TODO){
                   find { it.id == updateTask.id }?.description = updateTask.description
               } else {
                   remove(updateTask)
               }
            }

            val storedPositionTaskToUpdate =
                newListWithUpdatedTask.indexOfFirst { it.id == updateTask.id }

            taskAdapter.submitList(newListWithUpdatedTask)

            taskAdapter.notifyItemChanged(storedPositionTaskToUpdate)
        }
    }

    private fun initRecyclerView() {
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            selectedOption(task, option)
        }

        with(bind.rvTasks){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun selectedOption(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(
                    titleDialog = R.string.text_title_dialog_delete,
                    titleButton = R.string.text_dialog_button_confirm,
                    message = getString(R.string.text_message_dialog_delete),
                    onClick = {
                        deleteTask(task)
                    }
                )
            }

            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(), "Detalhes: ${task.description}", Toast.LENGTH_SHORT
                )
                    .show()
            }

            TaskAdapter.SELECT_NEXT -> {
                task.status = Status.DOING
                viewModel.updateTask(task)
            }
        }
    }

    private fun setPositionRecyclerView(){
        taskAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                bind.rvTasks.scrollToPosition(0)
            }
        })
    }

    private fun deleteTask(task: Task){
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserId())
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.text_success_delete_task,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun listEmpty(taskList: List<Task>){
        bind.textInfo.text = if(taskList.isEmpty()){
            getString(R.string.text_task_list_empty)
        } else {
            ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}