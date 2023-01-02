package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.Txn

@Dao
interface TxnDao {
    @Query("SELECT * FROM txn")
    suspend fun selectAll(): List<Txn>

    @Query("SELECT * FROM txn WHERE wallet_id=:walletId")
    suspend fun selectAllByWalletId(walletId: Long): List<Txn>

    @Query("SELECT * FROM txn WHERE wallet_id=:walletId AND token_id=:tokenId")
    suspend fun selectAllByWalletAndTokenId(
        walletId: Long,
        tokenId: Long
    ): List<Txn>

    @Query("SELECT * FROM txn WHERE is_success IS NULL")
    suspend fun selectAllPending(): List<Txn>

    @Query("SELECT * FROM txn WHERE id=:id")
    suspend fun selectById(id: Long): Txn

    @Query("SELECT * FROM txn WHERE txn_number=:txn")
    suspend fun selectByTxn(txn: String): Txn

    @Insert
    suspend fun insert(wallet: Txn): Long

    @Update
    suspend fun update(wallet: Txn): Int

    @Query("DELETE FROM txn WHERE id=:id")
    suspend fun delete(id: Long): Int
}
