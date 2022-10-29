package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.BlockChainDao
import com.chillieman.chilliewallet.db.entity.BlockChain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockChainRepository
@Inject constructor(
    private val blockChainDao: BlockChainDao
){
    fun insertBlockChain(blockChain: BlockChain) = blockChainDao.insert(blockChain)

    fun insertBlockChains(blockChains: List<BlockChain>) = blockChainDao.insertAll(blockChains)

    fun fetchAlBlockChains() = blockChainDao.selectAll()
}