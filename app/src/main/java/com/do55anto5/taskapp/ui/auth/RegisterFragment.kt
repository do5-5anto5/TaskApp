package com.do55anto5.taskapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.do55anto5.taskapp.databinding.FragmentRegisterBinding

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

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}