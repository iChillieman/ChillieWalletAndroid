package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChilliePair

@Dao
interface ChilliePairDao {
    @Query("SELECT * FROM pairs")
    suspend fun selectAll(): List<ChilliePair>

    @Query("SELECT * FROM pairs WHERE blockchain_id=:blockChainId")
    suspend fun selectAllByChainId(blockChainId: Long): List<ChilliePair>

    @Query("SELECT * FROM pairs WHERE id=:id")
    suspend fun selectById(id: Long): ChilliePair

    @Query("SELECT * FROM pairs WHERE token0=:tokenAddress OR token1=:tokenAddress")
    suspend fun selectByTokenAddress(tokenAddress: String): ChilliePair

    @Insert
    suspend fun insert(pair: ChilliePair): Long

    @Update
    suspend fun update(pair: ChilliePair): Int

    @Query("DELETE FROM pairs WHERE id=:id")
    suspend fun delete(id: Long): Int
}