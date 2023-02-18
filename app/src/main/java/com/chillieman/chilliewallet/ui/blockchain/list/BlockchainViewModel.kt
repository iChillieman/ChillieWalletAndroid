package com.chillieman.chilliewallet.ui.blockchain.list


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chillieman.chilliewallet.db.entity.Blockchain
import com.chillieman.chilliewallet.model.data.FullBlockChainData
import com.chillieman.chilliewallet.repository.BlockchainRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockchainViewModel
@Inject constructor(private val blockchainRepo: BlockchainRepository) : BaseViewModel() {
    private val _blockchainList = MutableLiveData<List<BlockchainSelectionItem>>(emptyList())
    val blockchainList: LiveData<List<BlockchainSelectionItem>>
        get() = _blockchainList

    private var blockchainJob: Job? = null

    override fun onCleared() {
        super.onCleared()

        blockchainJob?.cancel()
        blockchainJob = null
    }

    fun loadBlockchainItems() {
        blockchainJob?.cancel()
        blockchainJob = viewModelScope.launch {
            val itemsForList = mutableListOf<BlockchainSelectionItem>()
            // First get all BlockChain Data
            val blockchains = blockchainRepo.fetchAllBlockchains()
            blockchains.forEach { blockchain ->
                val headerText = if(blockchain.isTestnet) {
                    "[Testnet] ${blockchain.name} (ID: ${blockchain.id})"
                } else {
                    "${blockchain.name} (ID: ${blockchain.id})"
                }
                val blockchainLabel = BlockchainSelectionItem(
                    isHeader = true,
                    headerText = headerText
                )

                itemsForList.add(blockchainLabel)

                blockchainRepo.fetchAllBlockchainNodes(blockchain.id).forEach { node ->
                    itemsForList.add(
                        BlockchainSelectionItem(FullBlockChainData(blockchain, node))
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "ChillieWalletVM"
    }
}
