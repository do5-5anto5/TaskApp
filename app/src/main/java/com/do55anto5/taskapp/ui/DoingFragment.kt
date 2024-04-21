package com.do55anto5.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentDoingBinding
import com.do55anto5.taskapp.ui.adapter.TaskAdapter

class DoingFragment : Fragment() {

    private var _bind: FragmentDoingBinding? = null
    private val bind get() = _bind!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentDoingBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getTasks())
    }

    private fun initRecyclerView(taskList: List<Task>){
        taskAdapter = TaskAdapter(requireContext(), taskList)

        bind.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        bind.rvTasks.setHasFixedSize(true)
        bind.rvTasks.adapter = taskAdapter
    }

    private fun getTasks() = listOf(
        Task("0", "Validar informações na tela de cadastro", Status.DOING),
        Task("1", "Salvar foto do usuário no banco de dados", Status.DOING),
        Task("2", "Ajustar tela de produtos o app", Status.DOING),
        Task("3", "Criar opção de upload de imagem", Status.DOING),
        Task("4", "Permitir remover os produtos", Status.DOING)
    )

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}