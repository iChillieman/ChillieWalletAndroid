package com.chillieman.chilliewallet.ui.welcome.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.databinding.ItemSeedWordBinding

class WelcomeWalletCreateAdapter(private val wordList: List<String>) :
    RecyclerView.Adapter<WelcomeWalletCreateAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemSeedWordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(position + 1, wordList[position])
    }

    override fun getItemCount(): Int = wordList.size

    inner class WordViewHolder(private val binding: ItemSeedWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, word: String) {
            binding.seedIndex.text = index.toString()
            binding.seedWord.text = word
        }
    }

}
