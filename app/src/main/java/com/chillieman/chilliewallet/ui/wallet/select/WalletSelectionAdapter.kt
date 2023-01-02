package com.chillieman.chilliewallet.ui.wallet.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.databinding.ItemWalletBinding
import com.chillieman.chilliewallet.db.entity.ChillieWallet

class WalletSelectionAdapter(
    private val walletItems: List<WalletSelectionItem>,
    private val listener: OnWalletSelectedListener
) :
    RecyclerView.Adapter<WalletSelectionAdapter.WalletViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding = ItemWalletBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(walletItems[position])
    }

    override fun getItemCount(): Int = walletItems.size

    interface OnWalletSelectedListener {
        fun onWalletSelected(wallet: ChillieWallet)
    }

    inner class WalletViewHolder(private val binding: ItemWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(walletItem: WalletSelectionItem) {
            binding.walletSelectName.text = walletItem.wallet.name
            binding.walletSelectAddress.text = walletItem.wallet.address
            if (walletItem.walletBalance == "Loading") {
                binding.walletSelectBalance.visibility = View.INVISIBLE
                binding.progressBalanceLoading.visibility = View.VISIBLE
            } else {
                binding.walletSelectBalance.text = "${walletItem.walletBalance} BNB"
                binding.walletSelectBalance.visibility = View.VISIBLE
                binding.progressBalanceLoading.visibility = View.GONE
            }


            binding.root.setOnClickListener {
                listener.onWalletSelected(walletItem.wallet)
            }
        }
    }

}
