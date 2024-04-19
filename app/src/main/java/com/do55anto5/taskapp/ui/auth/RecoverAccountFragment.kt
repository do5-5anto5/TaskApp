package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentRecoverAccountBinding
import com.do55anto5.taskapp.util.initToolbar

class RecoverAccountFragment : Fragment() {

    private var _bind: FragmentRecoverAccountBinding? = null
    private val bind get() = _bind!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bind = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(bind.toolbar)
       initListeners()
    }

    private fun initListeners() {
        bind.btnSend.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = bind.editEmail.text.toString().trim()

        if (email.isNotEmpty()){
            Toast.makeText(requireContext(), "Happy Way!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), R.string.editEmail_isEmpty, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}