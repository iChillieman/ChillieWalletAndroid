package com.chillieman.chilliewallet.model.data

import com.chillieman.chilliewallet.db.entity.Blockchain
import com.chillieman.chilliewallet.db.entity.BlockchainNode

class FullBlockChainData(
    val blockchain: Blockchain,
    val node: BlockchainNode
)