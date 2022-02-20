package com.vivek.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vivek.todo.databinding.RowTodoItemBinding
import com.vivek.todo.dbwork.ToDoModel

class ToDoAdapter(val list: List<ToDoModel>):RecyclerView.Adapter<ToDoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val binding: RowTodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDoModel) {
            with(binding) {
                binding.model = item
                binding.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowTodoItemBinding.inflate(inflater)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount(): Int {
       return list.size
    }
}