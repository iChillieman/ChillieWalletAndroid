package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import io.reactivex.Single

@Dao
abstract class ChillieWalletDao {
    @Query("SELECT * FROM wallet")
    abstract fun selectAll(): Single<List<ChillieWallet>>

    @Query("SELECT * FROM wallet WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieWallet>

    @Query("SELECT * FROM wallet WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieWallet

    @Insert
    abstract fun insert(user: ChillieWallet): Long

    @Update
    abstract fun update(user: ChillieWallet): Int

    @Query("SELECT COUNT(*) FROM wallet")
    abstract fun count(): Single<Long>

    @Query("DELETE FROM wallet WHERE id=:id")
    abstract fun delete(id: Long) : Int
}

