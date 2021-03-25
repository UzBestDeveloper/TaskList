package com.developeruz.tasklist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developeruz.tasklist.R
import com.developeruz.tasklist.databinding.ItemTaskListBinding
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.utils.TaskListener

class TaskListAdapter(private var context: Context, private val clickListener: TaskListener) : ListAdapter<Task, TaskListAdapter.ViewHolder>(
    DiffTaskCallback()
) {

    class ViewHolder private constructor(private val binding: ItemTaskListBinding) : RecyclerView.ViewHolder(
        binding.root
    ){

        fun bind(item: Task, clickListener: TaskListener, context: Context) {
            setData(item,clickListener,context)
        }

        @SuppressLint("SetTextI18n")
        fun setData(item: Task, clickListener: TaskListener, context: Context) {
            binding.textViewName.text = item.name
            if (item.status == 0) {
                binding.textViewStatus.text = context.resources.getString(R.string.in_progress)
                binding.imageViewStatus.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_circle_in_progress))
                binding.viewLeft.background = ContextCompat.getDrawable(context,R.drawable.left_cornered_in_progress);
            }else{
                binding.textViewStatus.text = context.resources.getString(R.string.done)
                binding.imageViewStatus.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_circle_done))
                binding.viewLeft.background = ContextCompat.getDrawable(context,R.drawable.left_cornered_done);
            }
            binding.textViewDueDate.text = item.due_date
            binding.imageViewEdit.setOnClickListener { clickListener.onTaskEdited(item) }
            binding.linearLayout.setOnClickListener { clickListener.onTaskEdited(item) }
            binding.imageViewDelete.setOnClickListener { clickListener.onTaskDeleted(item) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTaskListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener,context)
    }

}

class DiffTaskCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}
