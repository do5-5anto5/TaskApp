package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentFormTaskBinding
import com.do55anto5.taskapp.util.initToolbar
import com.do55anto5.taskapp.util.showBottomSheet

class FormTaskFragment : Fragment() {

    private var _bind: FragmentFormTaskBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentFormTaskBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(bind.toolbar)
        initListeners()
    }

    private fun initListeners() {
        bind.btnSave.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val description = bind.editDesc.text.toString().trim()

        if (description.isNotEmpty()){
            Toast.makeText(requireContext(), "Happy Way!", Toast.LENGTH_SHORT).show()
        } else {
            showBottomSheet(message = R.string.editDesc_isEmpty)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}