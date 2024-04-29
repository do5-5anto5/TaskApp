package com.do55anto5.taskapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val selectedTask: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

        companion object{
            val SELECT_BACK: Int = 1
            val SELECT_REMOVE: Int = 2
            val SELECT_EDIT: Int = 3
            val SELECT_DETAILS: Int = 4
            val SELECT_NEXT: Int = 5

            private val DIFF_CALLBACK = object :  DiffUtil.ItemCallback<Task>() {
                override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                    return newItem.id == oldItem.id && newItem.description == oldItem.description
                }

                override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                    return newItem == oldItem && newItem.description == oldItem.description
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)

        holder.bind.txtDescription.text = task.description
        setIndicators(task, holder)

        with(holder.bind){
            btnDelete.setOnClickListener { selectedTask(task, SELECT_REMOVE) }
            btnEdit.setOnClickListener { selectedTask(task, SELECT_EDIT) }
            btnDetails.setOnClickListener { selectedTask(task, SELECT_DETAILS) }
        }
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {

            Status.TODO ->{
                with(holder.bind){
                    btnBack.isVisible = false
                    btnNext.setOnClickListener { selectedTask(task, SELECT_NEXT) }
                }
            }

            Status.DOING ->{

                with(holder.bind){
                    btnBack.setColorFilter(
                        ContextCompat.getColor(context, R.color.status_todo)
                    )
                    btnNext.setColorFilter(
                        ContextCompat.getColor(context, R.color.status_done)
                    )
                    btnNext.setOnClickListener { selectedTask(task, SELECT_NEXT)
                    }
                    btnBack.setOnClickListener { selectedTask(task, SELECT_BACK)
                    }
                }
            }

            Status.DONE -> {

                with (holder.bind){
                    btnNext.isVisible = false

                    btnBack.setOnClickListener { selectedTask(task, SELECT_BACK) }
                }
            }
        }
    }

    inner class MyViewHolder(val bind: ItemTaskBinding) : RecyclerView.ViewHolder(bind.root) {
    }
}