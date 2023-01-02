package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.BlockChain

@Dao
interface BlockChainDao {
    @Query("SELECT * FROM block_chain")
    suspend fun selectAll(): List<BlockChain>

    @Query("SELECT * FROM block_chain WHERE id=:id")
    suspend fun selectById(id: Long): BlockChain

    @Insert
    suspend fun insert(blockChain: BlockChain): Long

    @Insert
    suspend fun insertAll(blockChains: List<BlockChain>)

    @Update
    suspend fun update(blockChain: BlockChain): Int

    @Query("DELETE FROM block_chain WHERE id=:id")
    suspend fun delete(id: Long): Int
}
