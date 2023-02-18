package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.Balance

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balance")
    suspend fun selectAll(): List<Balance>

    @Query("SELECT * FROM balance WHERE wallet_id=:walletId")
    suspend fun selectAllForWallet(walletId: Long): List<Balance>

    @Query("SELECT * FROM balance WHERE id=:id")
    suspend fun selectById(id: Long): Balance

    @Insert
    suspend fun insert(balance: Balance): Long

    @Update
    suspend fun update(balance: Balance): Int

    @Query("DELETE FROM balance WHERE id=:id")
    suspend fun delete(id: Long): Int
}
