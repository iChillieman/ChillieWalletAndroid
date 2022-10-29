package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.Balance
import io.reactivex.Single

@Dao
abstract class BalanceDao {
    @Query("SELECT * FROM balance")
    abstract fun selectAll(): Single<List<Balance>>

    @Query("SELECT * FROM balance WHERE wallet_id=:walletId")
    abstract fun selectAllForWallet(walletId: Long): Single<List<Balance>>

    @Query("SELECT * FROM balance WHERE id=:id")
    abstract fun selectById(id: Long): Single<Balance>

    @Query("SELECT * FROM balance WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): Balance

    @Insert
    abstract fun insert(balance: Balance): Single<Long>

    @Update
    abstract fun update(balance: Balance): Single<Int>

    @Query("DELETE FROM balance WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}
