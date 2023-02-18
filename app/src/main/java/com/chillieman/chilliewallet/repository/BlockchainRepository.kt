package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.BlockchainDao
import com.chillieman.chilliewallet.db.dao.BlockchainNodeDao
import com.chillieman.chilliewallet.db.entity.Blockchain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockchainRepository
@Inject constructor(
    private val blockchainDao: BlockchainDao,
    private val blockchainNodeDao: BlockchainNodeDao
) {
    suspend fun insertBlockchain(blockchain: Blockchain) = blockchainDao.insert(blockchain)

    suspend fun insertBlockchains(blockchains: List<Blockchain>) =
        blockchainDao.insertAll(blockchains)

    suspend fun fetchAllBlockchains() = blockchainDao.selectAll()

    suspend fun fetchBlockchainById(blockchainId: Long) = blockchainDao.selectById(blockchainId)

    suspend fun fetchDefaultBlockchainNode(blockchainId: Long) = blockchainNodeDao.selectDefault(blockchainId)

    suspend fun fetchAllBlockchainNodes(blockchainId: Long) = blockchainNodeDao.selectAll()
}