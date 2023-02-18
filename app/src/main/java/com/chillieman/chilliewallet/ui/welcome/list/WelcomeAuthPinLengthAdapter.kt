package com.chillieman.chilliewallet.ui.welcome.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ItemPinLengthBinding

class WelcomeAuthPinLengthAdapter() :
    RecyclerView.Adapter<WelcomeAuthPinLengthAdapter.CircleViewHolder>() {
    var pinSize = 0

    @SuppressLint("NotifyDataSetChanged")
    fun updatePinSize(size: Int) {
        pinSize = size
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleViewHolder {
        val binding = ItemPinLengthBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CircleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CircleViewHolder, position: Int) {
        holder.bind(position, pinSize)
    }

    override fun getItemCount(): Int = 6


    inner class CircleViewHolder(private val binding: ItemPinLengthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, pinLength: Int) {
            if (position < pinLength) {
                binding.pinLengthBullet.setImageResource(R.drawable.bg_circle_filled)
            } else {
                binding.pinLengthBullet.setImageResource(R.drawable.bg_circle_thin_outline)
            }
        }
    }
}