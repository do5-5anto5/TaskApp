package com.do55anto5.taskapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.data.model.Status
import com.do55anto5.taskapp.data.model.Task
import com.do55anto5.taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val taskList: List<Task>
) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {


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
        val task = taskList[position]

        holder.bind.txtDescription.text = task.description
        setIndicators(task, holder)
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {

            Status.TODO -> holder.bind.btnBack.isVisible = false

            Status.DOING ->{
                holder.bind.btnBack.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_todo)
                )
                holder.bind.btnNext.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_done)
                )
            }

            Status.DONE -> holder.bind.btnNext.isVisible = false
        }
    }

    override fun getItemCount() = taskList.size

    inner class MyViewHolder(val bind: ItemTaskBinding) : RecyclerView.ViewHolder(bind.root) {
    }
}