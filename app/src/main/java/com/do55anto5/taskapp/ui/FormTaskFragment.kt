package com.do55anto5.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.FragmentFormTaskBinding
import com.do55anto5.taskapp.util.FirebaseHelper
import com.do55anto5.taskapp.util.initToolbar
import com.do55anto5.taskapp.util.showBottomSheet

class FormTaskFragment : BaseFragment() {

    private var _bind: FragmentFormTaskBinding? = null
    private val bind get() = _bind!!

    private lateinit var task: Task
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private val args: FormTaskFragmentArgs by navArgs()

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentFormTaskBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(bind.toolbar)
        getArgs()
        initListeners()
    }

    private fun getArgs() {
        args.task.let {
            if (it != null) {
                this.task = it
                configTask()
            }
        }
    }

    private fun initListeners() {

        bind.btnSave.setOnClickListener { validateData() }

        bind.rgStatus.setOnCheckedChangeListener { _, id ->
            status = when (id) {
                R.id.rbTodo -> Status.TODO
                R.id.rbDoing -> Status.DOING
                else -> Status.DONE
            }
        }
    }

    private fun configTask() {
        newTask = false
        status = task.status
        bind.textToolbar.setText(R.string.text_toolbar_update_form_task_fragment)

        bind.editDesc.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        bind.rgStatus.check(
            when (task.status) {
                Status.TODO -> R.id.rbTodo
                Status.DOING -> R.id.rbDoing
                else -> R.id.rbDone
            }
        )
    }

    private fun validateData() {
        val description = bind.editDesc.text.toString().trim()

        if (description.isNotEmpty()) {

            hideKeyBoard()

            bind.progressBar.isVisible = true

            if (newTask) task = Task()
            task.description = description
            task.status = status

            saveTask()

        } else {
            showBottomSheet(message = getString(R.string.editDesc_isEmpty))
        }
    }

    private fun saveTask() {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserId())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(), R.string.dialog_save_success_form_task_fragment,
                        Toast.LENGTH_SHORT
                    ).show()

                    if (newTask) {
                        findNavController().popBackStack()
                    } else {
                        viewModel.setUpdateTask(task)
                        findNavController().popBackStack()
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