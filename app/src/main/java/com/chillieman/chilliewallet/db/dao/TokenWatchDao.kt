package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.TokenWatch

@Dao
interface TokenWatchDao {
    @Query("SELECT * FROM token_wallet")
    suspend fun selectAll(): List<TokenWatch>

    @Query("SELECT * FROM token_wallet WHERE wallet_id=:walletId")
    suspend fun selectAllByWalletId(walletId: Long): List<TokenWatch>

    @Query("SELECT * FROM token_wallet WHERE id=:id")
    suspend fun selectById(id: Long): TokenWatch

    @Insert
    suspend fun insert(watch: TokenWatch): Long

    @Update
    suspend fun update(watch: TokenWatch): Int

    @Query("DELETE FROM token_wallet WHERE id=:id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM token_wallet")
    suspend fun clear(): Int
}