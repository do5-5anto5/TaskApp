package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.do55anto5.taskapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _bind: FragmentSplashBinding? = null
    private val bind get() = _bind

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentSplashBinding.inflate(inflater, container, false)
        return bind?.root
    }
}