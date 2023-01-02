package com.chillieman.chilliewallet.ui.welcome.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.databinding.ItemErrorBinding

class WelcomeAuthPasswordAdapter(private val errorList: List<Int>) :
    RecyclerView.Adapter<WelcomeAuthPasswordAdapter.ErrorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        val binding = ItemErrorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ErrorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        holder.bind(errorList[position])
    }

    override fun getItemCount(): Int = errorList.size

    inner class ErrorViewHolder(private val binding: ItemErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(errorId: Int) {
            binding.errorMessage.text = binding.root.context.getString(errorId)
        }
    }

}
