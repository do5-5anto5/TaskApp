package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentLoginBinding
import com.do55anto5.taskapp.databinding.FragmentSplashBinding

class LoginFragment : Fragment() {

    private var _bind: FragmentLoginBinding? = null
    private val bind get() = _bind
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentLoginBinding.inflate(inflater, container, false)
        return bind?.root
    }
}