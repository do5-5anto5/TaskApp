package com.do55anto5.taskapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val selectedTask: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

        companion object{
            val SELECT_REMOVE: Int = 2
            val SELECT_EDIT: Int = 3
            val SELECT_DETAILS: Int = 4

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

        with(holder.bind){
            btnDelete.setOnClickListener { selectedTask(task, SELECT_REMOVE) }
            btnEdit.setOnClickListener { selectedTask(task, SELECT_EDIT) }
            btnDetails.setOnClickListener { selectedTask(task, SELECT_DETAILS) }
        }
    }

    inner class MyViewHolder(val bind: ItemTaskBinding) : RecyclerView.ViewHolder(bind.root) {
    }
}