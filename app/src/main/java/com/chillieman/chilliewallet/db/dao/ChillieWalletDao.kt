package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import io.reactivex.Single

@Dao
abstract class ChillieWalletDao {
    @Query("SELECT * FROM chillie_wallet")
    abstract fun selectAll(): Single<List<ChillieWallet>>

    @Query("SELECT * FROM chillie_wallet WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieWallet>

    @Query("SELECT * FROM chillie_wallet WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieWallet

    @Insert
    abstract fun insert(wallet: ChillieWallet): Single<Long>

    @Update
    abstract fun update(wallet: ChillieWallet): Single<Int>

    @Query("SELECT COUNT(*) FROM chillie_wallet")
    abstract fun count(): Single<Long>

    @Query("DELETE FROM chillie_wallet WHERE id=:id")
    abstract fun delete(id: Long): Int
}
