package com.chillieman.chilliewallet.ui.main.wallet.tokenlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.databinding.ItemTokenBinding
import com.chillieman.chilliewallet.db.entity.Token

class TokenListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfTokens: List<TokenForList> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTokenBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TokenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val token = listOfTokens[position]
        holder as TokenViewHolder
        holder.bind(token)
    }

    override fun getItemCount(): Int = listOfTokens.size

    fun loadItems(newTokenList: List<TokenForList>) {
        listOfTokens = newTokenList

        //TODO: Setup DiffUtil here - Do not send out using notifyDataSetChanged !
        notifyDataSetChanged()
    }

    inner class TokenViewHolder(
        private val binding: ItemTokenBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(token: TokenForList) {
            binding.tvTokenName.text = token.name
            val balanceString = "${token.balance} ${token.symbol}"
            binding.tvTokenBalance.text = balanceString
            binding.tvTokenWorth.text = token.worth
        }
    }
}
