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

    @Query("SELECT * FROM txn WHERE wallet_id=:walletId AND token_address=:tokenAddress")
    abstract fun selectAllByWalletAndTokenAddress(
        walletId: Long,
        tokenAddress: String
    ): Single<List<Txn>>

    @Query("SELECT * FROM txn WHERE is_success IS NULL")
    abstract fun selectAllPending(): Single<List<Txn>>

    @Query("SELECT * FROM txn WHERE id=:id")
    abstract fun selectById(id: Long): Single<Txn>

    @Query("SELECT * FROM txn WHERE txn_number=:txn")
    abstract fun selectByTxn(txn: String): Single<Txn>

    @Query("SELECT * FROM txn WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): Txn

    @Insert
    abstract fun insert(wallet: Txn): Single<Long>

    @Update
    abstract fun update(wallet: Txn): Single<Int>

    @Query("DELETE FROM txn WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}
