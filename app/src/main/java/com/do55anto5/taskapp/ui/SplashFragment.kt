package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(this::checkAuth, 3000)

        auth = Firebase.auth
    }

    private fun checkAuth(){

        val currentUser = auth.currentUser

        if (currentUser != null) findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        else findNavController().navigate(R.id.action_splashFragment_to_authentication)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}