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
    private val taskList: List<Task>,
    private val selectedTask: (Task, Int) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

        companion object{
            val SELECT_BACK: Int = 1
            val SELECT_REMOVE: Int = 2
            val SELECT_EDIT: Int = 3
            val SELECT_DETAILS: Int = 4
            val SELECT_NEXT: Int = 5
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
        val task = taskList[position]

        holder.bind.txtDescription.text = task.description
        setIndicators(task, holder)

        holder.bind.btnDelete.setOnClickListener { selectedTask(task, SELECT_REMOVE) }
        holder.bind.btnEdit.setOnClickListener { selectedTask(task, SELECT_EDIT) }
        holder.bind.btnDetails.setOnClickListener { selectedTask(task, SELECT_DETAILS) }
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {

            Status.TODO ->{
                holder.bind.btnBack.isVisible = false


                holder.bind.btnNext.setOnClickListener { selectedTask(task, SELECT_NEXT) }
            }

            Status.DOING ->{
                holder.bind.btnBack.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_todo)
                )
                holder.bind.btnNext.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_done)
                )

                holder.bind.btnNext.setOnClickListener { selectedTask(task, SELECT_NEXT) }
                holder.bind.btnBack.setOnClickListener { selectedTask(task, SELECT_BACK) }
            }

            Status.DONE -> {
                holder.bind.btnNext.isVisible = false


                holder.bind.btnBack.setOnClickListener { selectedTask(task, SELECT_BACK) }
            }
        }
    }

    override fun getItemCount() = taskList.size

    inner class MyViewHolder(val bind: ItemTaskBinding) : RecyclerView.ViewHolder(bind.root) {
    }
}