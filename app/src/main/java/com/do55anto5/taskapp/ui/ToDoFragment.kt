package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentToDoBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ToDoFragment : Fragment() {

    private var _bind: FragmentToDoBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bind = FragmentToDoBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reference = Firebase.database.reference
        auth = Firebase.auth

        initListener()

        initRecyclerView()
        getTasks()
    }

    private fun initListener() {
        bind.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
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
                Toast.makeText(
                    requireContext(), "Removendo ${task.description}", Toast.LENGTH_SHORT
                ).show()
            }

            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(
                    requireContext(), "Editando ${task.description}", Toast.LENGTH_SHORT
                )
                    .show()
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT
                )
                    .show()
            }

            TaskAdapter.SELECT_NEXT -> {
                Toast.makeText(
                    requireContext(), "Próximo ${task.description}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getTasks(){
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()
                    for (ds in snapshot.children){
                        val task = ds.getValue(Task::class.java) as Task
                        taskList.add(task)
                    }
                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}