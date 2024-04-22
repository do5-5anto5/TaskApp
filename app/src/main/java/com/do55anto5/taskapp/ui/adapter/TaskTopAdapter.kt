package com.do55anto5.taskapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.ItemTaskTopBinding

class TaskTopAdapter(
    private val selectedTaskTop: (Task, Int) -> Unit
) : ListAdapter<Task, TaskTopAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
            ItemTaskTopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)

        holder.bind.txtDescription.text = task.description

        holder.bind.btnDelete.setOnClickListener { selectedTaskTop(task, SELECT_REMOVE) }
        holder.bind.btnEdit.setOnClickListener { selectedTaskTop(task, SELECT_EDIT) }
        holder.bind.btnDetails.setOnClickListener { selectedTaskTop(task, SELECT_DETAILS) }
    }

    inner class MyViewHolder(val bind: ItemTaskTopBinding) : RecyclerView.ViewHolder(bind.root) {
    }
}