package com.do55anto5.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.FragmentHomeBinding
import com.do55anto5.taskapp.databinding.FragmentLoginBinding
import com.do55anto5.taskapp.ui.adapter.ViewPageAdapter
import com.do55anto5.taskapp.util.showBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    private var _bind: FragmentHomeBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentHomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initListeners()
        initTabs()
    }

    private fun initListeners(){
        bind.btnLogout.setOnClickListener{
            showBottomSheet(
                titleButton = R.string.dialog_button_confirm_logout,
                titleDialog = R.string.dialog_title_confirm_logout,
                message = getString(R.string.dialog_message_confirm_logout),
                onClick = {
                    auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_authentication)
                }
            )
        }
    }

    private fun initTabs(){
        val pageAdapter = ViewPageAdapter(requireActivity())
        bind.viewPager.adapter = pageAdapter

        pageAdapter.addFragment(ToDoFragment(), R.string.status_task_todo)
        pageAdapter.addFragment(DoingFragment(), R.string.status_task_doing)
        pageAdapter.addFragment(DoneFragment(), R.string.status_task_done)

        bind.viewPager.offscreenPageLimit = pageAdapter.itemCount

        TabLayoutMediator(bind.tabs, bind.viewPager) {tab, position ->
            tab.text = getString(pageAdapter.getTitle(position))
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}