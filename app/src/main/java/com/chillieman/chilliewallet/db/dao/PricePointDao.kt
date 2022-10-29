package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.PricePoint
import io.reactivex.Single

@Dao
abstract class PricePointDao {
    @Query("SELECT * FROM historical_price")
    abstract fun selectAll(): Single<List<PricePoint>>

    @Query("SELECT * FROM historical_price WHERE token_address=:tokenAddress")
    abstract fun selectAllByTokenAddress(tokenAddress: String): Single<List<PricePoint>>

    @Query("SELECT * FROM historical_price WHERE id=:id")
    abstract fun selectById(id: Long): Single<PricePoint>

    @Query("SELECT * FROM historical_price WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): PricePoint

    @Insert
    abstract fun insert(price: PricePoint): Single<Long>

    @Update
    abstract fun update(price: PricePoint): Single<Int>

    @Query("DELETE FROM historical_price WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}
