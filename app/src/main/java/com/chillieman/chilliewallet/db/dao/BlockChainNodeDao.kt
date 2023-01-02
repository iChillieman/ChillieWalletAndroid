package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.BlockChainNode

@Dao
interface BlockChainNodeDao {
    @Query("SELECT * FROM block_chain_node")
    suspend fun selectAll(): List<BlockChainNode>

    @Query("SELECT * FROM block_chain_node WHERE id=:id")
    suspend fun selectById(id: Long): BlockChainNode

    @Insert
    suspend fun insert(blockChainNode: BlockChainNode): Long

    @Update
    suspend fun update(blockChainNode: BlockChainNode): Int

    @Query("DELETE FROM block_chain_node WHERE id=:id")
    suspend fun delete(id: Long): Int
}
