package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentRegisterBinding
import com.do55anto5.taskapp.util.initToolbar
import com.do55anto5.taskapp.util.showBottomSheet

class RegisterFragment : Fragment() {

    private var _bind: FragmentRegisterBinding? = null
    private val bind get() = _bind!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentRegisterBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(bind.toolbar)
        initListeners()
    }

    private fun initListeners() {
        bind.btnRegister.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = bind.editEmail.text.toString().trim()
        val password = bind.editPassword.text.toString().trim()

        if (email.isNotEmpty()){
            if(password.isNotEmpty()){
                Toast.makeText(requireContext(), "Happy Way!", Toast.LENGTH_SHORT).show()
            } else {
                showBottomSheet(message = getString(R.string.editPassword_isEmpty))
            }
        } else {
            showBottomSheet(message = getString(R.string.editEmail_isEmpty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}