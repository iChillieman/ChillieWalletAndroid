package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.BlockchainNode

@Dao
interface BlockchainNodeDao {
    @Query("SELECT * FROM block_chain_node")
    suspend fun selectAll(): List<BlockchainNode>

    @Query("SELECT * FROM block_chain_node WHERE blockchain_id=:blockchainId")
    suspend fun selectAllByBlockchainId(blockchainId: Long): List<BlockchainNode>

    @Query("SELECT * FROM block_chain_node WHERE blockchain_id=:blockchainId LIMIT 1")
    suspend fun selectDefault(blockchainId: Long): BlockchainNode

    @Query("SELECT * FROM block_chain_node WHERE id=:id")
    suspend fun selectById(id: Long): BlockchainNode

    @Insert
    suspend fun insert(blockchainNode: BlockchainNode): Long

    @Update
    suspend fun update(blockchainNode: BlockchainNode): Int

    @Query("DELETE FROM block_chain_node WHERE id=:id")
    suspend fun delete(id: Long): Int
}
