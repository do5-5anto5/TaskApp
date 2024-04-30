package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.util.Log
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
import com.do55anto5.taskapp.databinding.FragmentTasksBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter
import com.do55anto5.taskapp.util.StateView
import com.do55anto5.taskapp.util.showBottomSheet


class TasksFragment : Fragment() {

    private var _bind: FragmentTasksBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by activityViewModels()

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

        viewModel.getTasks()
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

        viewModel.taskList.observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.OnLoading -> {
                    bind.progressBar.isVisible = true
                }
                is StateView.OnSuccess -> {

                    val taskList =  stateView.data?.filter { it.status == Status.TODO }

                    bind.progressBar.isVisible = false
                    listEmpty(taskList ?: emptyList())

                    taskAdapter.submitList(taskList)
                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                    bind.progressBar.isVisible = false
                }
            }
        }

        viewModel.taskInsert.observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.OnLoading -> {
                    bind.progressBar.isVisible = true
                }
                is StateView.OnSuccess -> {
                    bind.progressBar.isVisible = false

                    if (stateView.data?.status == Status.TODO) {

                        val adapterCurrentList = taskAdapter.currentList

                        val newListWithUpdatedTask = adapterCurrentList.toMutableList().apply {
                            add(0, stateView.data)
                        }

                        taskAdapter.submitList(newListWithUpdatedTask)

                        setPositionRecyclerView()
                    }
                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                    bind.progressBar.isVisible = false
                }
            }
        }

        viewModel.taskUpdate.observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.OnLoading -> {
                    bind.progressBar.isVisible = true
                }
                is StateView.OnSuccess -> {
                    Log.i("TESTUPDATE", "observeViewModel: ${stateView.data?.description}")

                    bind.progressBar.isVisible = false

                    val adapterCurrentList = taskAdapter.currentList

                    val newListWithUpdatedTask = adapterCurrentList.toMutableList().apply {
                        if (!adapterCurrentList.contains(stateView.data) && stateView.data?.status == Status.TODO){
                            add(0, stateView.data)
                            setPositionRecyclerView()
                        }

                        if (stateView.data?.status == Status.TODO){
                            find { it.id == stateView.data.id }?.description = stateView.data.description
                        } else {
                            remove(stateView.data)
                        }
                    }

                    val storedPositionTaskToUpdate =
                        newListWithUpdatedTask.indexOfFirst { it.id == stateView.data?.id }

                    taskAdapter.submitList(newListWithUpdatedTask)

                    taskAdapter.notifyItemChanged(storedPositionTaskToUpdate)

                    listEmpty(newListWithUpdatedTask)
                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                    bind.progressBar.isVisible = false
                }
            }


        }

        viewModel.taskDelete.observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.OnLoading -> {
                    bind.progressBar.isVisible = true
                }
                is StateView.OnSuccess -> {
                    bind.progressBar.isVisible = false

                    Toast.makeText(requireContext(),
                        R.string.text_success_delete_task,
                        Toast.LENGTH_SHORT
                    ).show()

                    val adapterCurrentList = taskAdapter.currentList
                    val newListWithUpdatedTask = adapterCurrentList.toMutableList().apply {
                        remove(stateView.data)
                    }

                    taskAdapter.submitList(newListWithUpdatedTask)

                    listEmpty(newListWithUpdatedTask)
                }
                is StateView.OnError -> {
                    bind.progressBar.isVisible = false
                }
            }
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
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(
                    titleDialog = R.string.text_title_dialog_delete,
                    titleButton = R.string.text_dialog_button_confirm,
                    message = getString(R.string.text_message_dialog_delete),
                    onClick = {
                        viewModel.deleteTask(task)
                    }
                )
            }

            TaskAdapter.SELECT_EDIT -> {
                val action = TasksFragmentDirections
                    .actionTasksFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(), "Detalhes: ${task.description}", Toast.LENGTH_SHORT
                )
                    .show()
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