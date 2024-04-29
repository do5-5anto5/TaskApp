package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentRegisterBinding
import com.do55anto5.taskapp.ui.BaseFragment
import com.do55anto5.taskapp.util.FirebaseHelper
import com.do55anto5.taskapp.util.initToolbar
import com.do55anto5.taskapp.util.showBottomSheet

class RegisterFragment : BaseFragment() {

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

                hideKeyBoard()

                bind.progressBar.isVisible = true

                userRegister(email, password)
            } else {
                showBottomSheet(message = getString(R.string.editPassword_isEmpty))
            }
        } else {
            showBottomSheet(message = getString(R.string.editEmail_isEmpty))
        }
    }

    private fun userRegister(email: String, password: String){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {

                    Log.i("INFOTEST", "loginUser: ${task.exception?.message}")

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