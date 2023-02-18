package com.chillieman.chilliewallet.ui.blockchain.list

import com.chillieman.chilliewallet.model.data.FullBlockChainData

class BlockchainSelectionItem(
    val blockchainData: FullBlockChainData? = null,
    val isHeader: Boolean = false,
    val headerText: String = ""
)
