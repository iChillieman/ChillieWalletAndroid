package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.ChillieWallet

@Dao
interface ChillieWalletDao {
    @Query("SELECT * FROM chillie_wallet")
    suspend fun selectAll(): List<ChillieWallet>

    @Query("SELECT * FROM chillie_wallet WHERE id=:id")
    suspend fun selectById(id: Long): ChillieWallet

    @Insert
    suspend fun insert(wallet: ChillieWallet): Long

    @Update
    suspend fun update(wallet: ChillieWallet): Int

    @Query("SELECT COUNT(*) FROM chillie_wallet")
    suspend fun count(): Long

    @Query("DELETE FROM chillie_wallet WHERE id=:id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM chillie_wallet")
    suspend fun clear(): Int
}
