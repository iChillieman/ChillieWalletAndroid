package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieOrder

@Dao
interface ChillieOrderDao {
    @Query("SELECT * FROM chillie_order")
    suspend fun selectAll(): List<ChillieOrder>

    @Query("SELECT * FROM chillie_order WHERE wallet_id=:walletId")
    suspend fun selectAllByWalletId(walletId: Long): List<ChillieOrder>

    @Query("SELECT * FROM chillie_order WHERE token_id=:tokenId")
    suspend fun selectAllByTokenId(tokenId: Long): List<ChillieOrder>

    @Query("SELECT * FROM chillie_order WHERE id=:id")
    suspend fun selectById(id: Long): ChillieOrder

    @Insert
    suspend fun insert(chillieOrder: ChillieOrder): Long

    @Update
    suspend fun update(chillieOrder: ChillieOrder): Int

    @Query("DELETE FROM chillie_order WHERE id=:id")
    suspend fun delete(id: Long): Int
}
