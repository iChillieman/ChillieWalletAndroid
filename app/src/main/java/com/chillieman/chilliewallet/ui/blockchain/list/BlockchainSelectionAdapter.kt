package com.chillieman.chilliewallet.ui.blockchain.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliewallet.databinding.ItemBlockchainBinding
import com.chillieman.chilliewallet.databinding.ItemHeaderBinding
import com.chillieman.chilliewallet.model.data.FullBlockChainData

class BlockchainSelectionAdapter(
    private val blockchainItems: List<BlockchainSelectionItem>,
    private val listener: OnBlockchainSelectedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_HEADER ->
                HeaderViewHolder(ItemHeaderBinding.inflate(inflater, parent, false))
            ITEM_BLOCKCHAIN -> BlockchainViewHolder(
                ItemBlockchainBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> error("Yikes... Tell Chillieman he fucked up on Blockchain Adapter ITEM_TYPE")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = blockchainItems[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(item.headerText)
            is BlockchainViewHolder -> holder.bind(
                item.blockchainData
                    ?: error("Chillieman Fucked up - BlockChain Data Missing in BlockchainAdapter")
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = blockchainItems[position]
        return if (item.blockchainData != null) {
            ITEM_BLOCKCHAIN
        } else if (item.isHeader && item.headerText.isNotBlank()) {
            ITEM_HEADER
        } else {
            error("Chillieman Fucked up - Tell him that BlockchainSelectionItem is not valid.")
        }
    }

    override fun getItemCount(): Int = blockchainItems.size

    interface OnBlockchainSelectedListener {
        fun onBlockchainSelected(blockchainData: FullBlockChainData)
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.tvHeaderTitle.text = title
        }
    }

    inner class BlockchainViewHolder(private val binding: ItemBlockchainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FullBlockChainData) {

            binding.root.setOnClickListener {
                // TODO - CHILLIEMAN - Implement Click Listener!
                listener.onBlockchainSelected(data)
                Toast.makeText(binding.root.context, "Coming Soon!", Toast.LENGTH_SHORT)
            }
        }
    }

    companion object {
        private const val ITEM_HEADER = 1
        private const val ITEM_BLOCKCHAIN = 2
    }
}
