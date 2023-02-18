package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.Blockchain

@Dao
interface BlockchainDao {
    @Query("SELECT * FROM block_chain")
    suspend fun selectAll(): List<Blockchain>

    @Query("SELECT * FROM block_chain WHERE id=:id")
    suspend fun selectById(id: Long): Blockchain

    @Insert
    suspend fun insert(blockchain: Blockchain): Long

    @Insert
    suspend fun insertAll(blockchains: List<Blockchain>)

    @Update
    suspend fun update(blockchain: Blockchain): Int

    @Query("DELETE FROM block_chain WHERE id=:id")
    suspend fun delete(id: Long): Int
}
