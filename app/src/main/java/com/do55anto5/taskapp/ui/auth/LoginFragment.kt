package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentLoginBinding
import com.do55anto5.taskapp.ui.BaseFragment
import com.do55anto5.taskapp.util.FirebaseHelper
import com.do55anto5.taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : BaseFragment() {

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

                hideKeyBoard()

                bind.progressBar.isVisible = true

                userLogin(email, password)

            } else {
            showBottomSheet(message = getString(R.string.editPassword_isEmpty))
            }
        } else {
            showBottomSheet(message = getString(R.string.editEmail_isEmpty))
        }
    }

    private fun userLogin(email: String, password: String){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    bind.progressBar.isVisible = false

                    showBottomSheet(message = getString(
                        FirebaseHelper.translateFirebaseAuthErrorMessagesToPortuguese(
                            task.exception?.message.toString())))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}