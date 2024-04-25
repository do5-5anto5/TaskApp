package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentFormTaskBinding
import com.do55anto5.taskapp.util.initToolbar
import com.do55anto5.taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FormTaskFragment : Fragment() {

    private var _bind: FragmentFormTaskBinding? = null
    private val bind get() = _bind!!

    private lateinit var task: Task
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentFormTaskBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reference = Firebase.database.reference
        auth = Firebase.auth

        initToolbar(bind.toolbar)
        initListeners()
    }

    private fun initListeners() {

        bind.btnSave.setOnClickListener { validateData() }

        bind.rgStatus.setOnCheckedChangeListener { _, id ->
            status = when(id){
                R.id.rbTodo -> Status.TODO
                R.id.rbDoing -> Status.DOING
                else -> Status.DONE
            }
        }
    }

    private fun validateData() {
        val description = bind.editDesc.text.toString().trim()

        if (description.isNotEmpty()){

            bind.progressBar.isVisible = true

            if(newTask) task = Task()
            task.id = reference.database.reference.push().key ?: ""

            task.description = description
            task.status = status

            saveTask()

        } else {
            showBottomSheet(message = getString(R.string.editDesc_isEmpty))
        }
    }

    private fun saveTask () {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.dialog_save_success_form_task_fragment,
                        Toast.LENGTH_SHORT).show()

                    if (newTask){
                        findNavController().popBackStack()
                    } else {
                        bind.progressBar.isVisible = false
                    }

                } else {
                    bind.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.generic_error))
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}