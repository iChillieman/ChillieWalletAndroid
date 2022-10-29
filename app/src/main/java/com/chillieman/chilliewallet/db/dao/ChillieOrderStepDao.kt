package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieOrderStep
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ChillieOrderStepDao {
    @Query("SELECT * FROM chillie_order_step")
    abstract fun selectAll(): Single<List<ChillieOrderStep>>

    @Query("SELECT * FROM chillie_order_step WHERE order_id=:orderId")
    abstract fun selectAllByChillieOrderId(orderId: Long): Single<List<ChillieOrderStep>>

    @Query("SELECT * FROM chillie_order_step WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieOrderStep>

    @Query("SELECT * FROM chillie_order_step WHERE txn_id=:txnId OR txn_fee_id=:txnId")
    abstract fun selectByTxnId(txnId: Long): Single<ChillieOrderStep>

    @Query("SELECT * FROM chillie_order_step WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieOrderStep

    @Insert
    abstract fun insertAll(orderStep: List<ChillieOrderStep>): Completable

    @Insert
    abstract fun insert(orderStep: ChillieOrderStep): Single<Long>

    @Update
    abstract fun update(orderStep: ChillieOrderStep): Single<Int>

    @Query("DELETE FROM chillie_order_step WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}
