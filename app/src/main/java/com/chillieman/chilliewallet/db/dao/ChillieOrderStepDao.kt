package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieOrderStep
import io.reactivex.Single

@Dao
abstract class ChillieOrderStepDao {
    @Query("SELECT * FROM chillie_order_step")
    abstract fun selectAll(): Single<List<ChillieOrderStep>>

    @Query("SELECT * FROM chillie_order_step WHERE order_id=:orderId")
    abstract fun selectAllByChillieOrderId(orderId: Long): Single<List<ChillieOrderStep>>

    @Query("SELECT * FROM chillie_order_step WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieOrderStep>

    @Query("SELECT * FROM chillie_order_step WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieOrderStep

    @Insert
    abstract fun insertSynchronously(wallet: ChillieOrderStep): Long

    @Update
    abstract fun updateSynchronously(wallet: ChillieOrderStep): Int

    @Query("DELETE FROM chillie_order_step WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
