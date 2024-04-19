package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentLoginBinding
import com.do55anto5.taskapp.util.showBottomSheet

class LoginFragment : Fragment() {

    private var _bind: FragmentLoginBinding? = null
    private val bind get() = _bind!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentLoginBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        bind.btnLogin.setOnClickListener {
            validateData()
        }

        bind.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        bind.btnRecover.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }
    }

    private fun validateData() {
        val email = bind.editEmail.text.toString().trim()
        val password = bind.editPassword.text.toString().trim()

        if (email.isNotEmpty()){
            if(password.isNotEmpty()){
                findNavController().navigate(R.id.action_global_homeFragment)
                Toast.makeText(requireContext(), "Happy Way!", Toast.LENGTH_SHORT).show()
            } else {
            showBottomSheet(message = R.string.editPassword_isEmpty)
            }
        } else {
            showBottomSheet(message = R.string.editEmail_isEmpty)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}