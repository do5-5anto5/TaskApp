package com.do55anto5.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentToDoBinding


class ToDoFragment : Fragment() {

    private var _bind: FragmentToDoBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentToDoBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener(){
        bind.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}