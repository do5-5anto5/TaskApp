package com.do55anto5.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentDoneBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter

class DoneFragment : Fragment() {

    private var _bind: FragmentDoneBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentDoneBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getTasks())
    }

    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(requireContext(), taskList) { task, option ->
            selectedOption(task, option)
        }

        bind.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        bind.rvTasks.setHasFixedSize(true)
        bind.rvTasks.adapter = taskAdapter
    }

    private fun selectedOption(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_BACK -> {
                Toast.makeText(
                    requireContext(), "Voltando ${task.description}", Toast.LENGTH_SHORT)
                    .show()
            }

            TaskAdapter.SELECT_REMOVE -> {
                Toast.makeText(
                    requireContext(),
                    "Removendo ${task.description}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(
                    requireContext(), "Editando ${task.description}", Toast.LENGTH_SHORT)
                    .show()
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getTasks() = listOf(
        Task("0", "Criar adapter de contatos", Status.DONE),
        Task("1", "Criar dialog padrão para o app", Status.DONE),
        Task("2", "Refatorar código da classe de tarefas", Status.DONE),
        Task("3", "Publicar app na loja", Status.DONE),
        Task("4", "Atualizar dependêcias do app", Status.DONE)
    )

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}