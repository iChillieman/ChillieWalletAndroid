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

    @Query("SELECT * FROM chillie_order WHERE token_address=:tokenAddress")
    abstract fun selectAllByTokenId(tokenAddress: Long): Single<List<ChillieOrder>>

    @Query("SELECT * FROM chillie_order WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieOrder>

    @Query("SELECT * FROM chillie_order WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieOrder

    @Insert
    abstract fun insert(chillieOrder: ChillieOrder): Single<Long>

    @Update
    abstract fun update(chillieOrder: ChillieOrder): Single<Int>

    @Query("DELETE FROM chillie_order WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
