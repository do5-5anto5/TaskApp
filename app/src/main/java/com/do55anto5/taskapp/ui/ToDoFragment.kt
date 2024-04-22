package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentToDoBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter


class ToDoFragment : Fragment() {

    private var _bind: FragmentToDoBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

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
        val taskList = listOf(
            Task("0", "Criar nova tela do app", Status.TODO),
            Task("1", "Validar informações na tela de login", Status.TODO),
            Task("2", "Adicionar nova funcionalidade no app", Status.TODO),
            Task("3", "Salvar token localmente", Status.TODO),
            Task("4", "Criar funcionalidade de logout do app", Status.TODO)
        )

        taskAdapter.submitList(taskList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}