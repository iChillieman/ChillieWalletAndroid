package com.chillieman.chilliewallet.ui.welcome.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ItemPinNumberBinding

class WelcomeAuthPinAdapter(private val listener: PinNumberListener) :
    RecyclerView.Adapter<WelcomeAuthPinAdapter.PinViewHolder>() {
    private var listOfSelections: List<String> =
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", PIN_BACK_BUTTON, "0", PIN_CLEAR_BUTTON)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinViewHolder {
        val binding = ItemPinNumberBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PinViewHolder, position: Int) {
        holder.bind(listOfSelections[position])
    }

    override fun getItemCount(): Int = listOfSelections.size


    interface PinNumberListener {
        fun onPinNumberPressed(number: String)
        fun onPinBackPressed()
        fun onPinClearPressed()
    }

    inner class PinViewHolder(private val binding: ItemPinNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(number: String) {
            when (number) {
                PIN_BACK_BUTTON -> {
                    binding.number.text = ""
                    binding.number.setBackgroundResource(R.drawable.ic_baseline_arrow_back_24)
                    binding.touchArea.setOnClickListener {
                        listener.onPinBackPressed()
                    }
                }
                PIN_CLEAR_BUTTON -> {
                    binding.number.text = PIN_CLEAR_BUTTON_VALUE
                    binding.touchArea.setOnClickListener {
                        listener.onPinClearPressed()
                    }
                }
                else -> {
                    binding.number.text = number
                    binding.touchArea.setOnClickListener {
                        listener.onPinNumberPressed(number)
                    }
                }
            }
        }
    }


    companion object {
        const val PIN_BACK_BUTTON = "back"
        const val PIN_CLEAR_BUTTON = "clear"
        const val PIN_CLEAR_BUTTON_VALUE = "C"
    }
}