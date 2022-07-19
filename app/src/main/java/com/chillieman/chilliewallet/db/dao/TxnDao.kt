package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.Txn
import io.reactivex.Single

@Dao
abstract class TxnDao {
    @Query("SELECT * FROM txn")
    abstract fun selectAll(): Single<List<Txn>>

    @Query("SELECT * FROM txn WHERE wallet_id=:walletId")
    abstract fun selectAllByWalletId(walletId: Long): Single<List<Txn>>

    @Query("SELECT * FROM txn WHERE token_id=:tokenId")
    abstract fun selectAllByTokenId(tokenId: Long): Single<List<Txn>>

    @Query("SELECT * FROM txn WHERE id=:id")
    abstract fun selectById(id: Long): Single<Txn>

    @Query("SELECT * FROM txn WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): Txn

    @Insert
    abstract fun insertSynchronously(wallet: Txn): Long

    @Update
    abstract fun updateSynchronously(wallet: Txn): Int

    @Query("DELETE FROM txn WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
