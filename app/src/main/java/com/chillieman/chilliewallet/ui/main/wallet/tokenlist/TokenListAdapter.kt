package com.chillieman.chilliewallet.ui.main.wallet.tokenlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.ItemTokenBinding
import com.chillieman.chilliewallet.definitions.TokenDefinitions

class TokenListAdapter(val context: Context, listener: TokenSelectedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            token.logoUrl?.let {
                val imgView = binding.imgTokenIcon
                when (it) {
                    TokenDefinitions.ChillieWallet.LOGO_URL -> {
                        Glide.with(context).load(R.mipmap.ic_chillie_wallet_round).into(imgView)
                    }
                    TokenDefinitions.PixlToken.LOGO_URL -> {
                        Glide.with(context).load(R.drawable.pixl_logo).into(imgView)
                    }
                    else -> {
                        Glide.with(context).load(it).into(imgView)
                    }
                }
            }
        }
    }
}
