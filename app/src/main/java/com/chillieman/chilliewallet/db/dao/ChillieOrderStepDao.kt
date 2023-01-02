package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieOrderStep

@Dao
interface ChillieOrderStepDao {
    @Query("SELECT * FROM chillie_order_step")
    suspend fun selectAll(): List<ChillieOrderStep>

    @Query("SELECT * FROM chillie_order_step WHERE order_id=:orderId")
    suspend fun selectAllByChillieOrderId(orderId: Long): List<ChillieOrderStep>

    @Query("SELECT * FROM chillie_order_step WHERE id=:id")
    suspend fun selectById(id: Long): ChillieOrderStep

    @Query("SELECT * FROM chillie_order_step WHERE txn_id=:txnId OR txn_fee_id=:txnId")
    suspend fun selectByTxnId(txnId: Long): ChillieOrderStep

    @Insert
    suspend fun insertAll(orderStep: List<ChillieOrderStep>)

    @Insert
    suspend fun insert(orderStep: ChillieOrderStep): Long

    @Update
    suspend fun update(orderStep: ChillieOrderStep): Int

    @Query("DELETE FROM chillie_order_step WHERE id=:id")
    suspend fun delete(id: Long): Int
}
