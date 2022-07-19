package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieOrder
import io.reactivex.Single

@Dao
abstract class ChillieOrderDao {
    @Query("SELECT * FROM chillie_order")
    abstract fun selectAll(): Single<List<ChillieOrder>>

    @Query("SELECT * FROM chillie_order WHERE wallet_id=:walletId")
    abstract fun selectAllByWalletId(walletId: Long): Single<List<ChillieOrder>>

    @Query("SELECT * FROM chillie_order WHERE token_id=:tokenId")
    abstract fun selectAllByTokenId(tokenId: Long): Single<List<ChillieOrder>>

    @Query("SELECT * FROM chillie_order WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieOrder>

    @Query("SELECT * FROM chillie_order WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieOrder

    @Insert
    abstract fun insertSynchronously(wallet: ChillieOrder): Long

    @Update
    abstract fun updateSynchronously(wallet: ChillieOrder): Int

    @Query("DELETE FROM chillie_order WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
