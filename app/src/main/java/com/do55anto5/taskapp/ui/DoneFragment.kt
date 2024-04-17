package com.do55anto5.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.do55anto5.taskapp.databinding.FragmentDoneBinding

class DoneFragment : Fragment() {

    private var _bind: FragmentDoneBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentDoneBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}