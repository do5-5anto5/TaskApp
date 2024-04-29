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
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentDoingBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter
import com.do55anto5.taskapp.util.FirebaseHelper
import com.do55anto5.taskapp.util.showBottomSheet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DoingFragment : Fragment() {

    private var _bind: FragmentDoingBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bind = FragmentDoingBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initRecyclerView()
        getTasks()
    }

    private fun observeViewModel(){
        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->
            if(updateTask.status == Status.DOING) {

                val adapterCurrentList = taskAdapter.currentList

                val newListWithUpdatedTask = adapterCurrentList.toMutableList().apply {
                    find { it.id == updateTask.id }?.description = updateTask.description
                }

                val storedPositionTaskToUpdate =
                    newListWithUpdatedTask.indexOfFirst { it.id == updateTask.id }

                taskAdapter.submitList(newListWithUpdatedTask)

                taskAdapter.notifyItemChanged(storedPositionTaskToUpdate)
            }
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
            TaskAdapter.SELECT_BACK -> {
                task.status = Status.TODO
                updateTask(task)
            }

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
                    requireContext(), "Detalhes: ${task.description}", Toast.LENGTH_SHORT)
                    .show()
            }

            TaskAdapter.SELECT_NEXT -> {
                task.status = Status.DONE
                updateTask(task)
            }
        }
    }

    private fun getTasks(){
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserId())
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()
                    for (ds in snapshot.children){
                        val task = ds.getValue(Task::class.java) as Task
                        if (task.status == Status.DOING){
                            taskList.add(task)
                        }
                    }

                    bind.progressBar.isVisible = false
                    listEmpty(taskList)

                    taskList.reverse()
                    taskAdapter.submitList(taskList)
                }

                private fun listEmpty(taskList: List<Task>){
                    bind.textInfo.text = if(taskList.isEmpty()){
                        getString(R.string.text_task_list_empty)
                    } else {
                        ""
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT).show()
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

    private fun updateTask(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserId())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.dialog_update_success,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}