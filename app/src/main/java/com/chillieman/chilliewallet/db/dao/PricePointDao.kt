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

    @Query("SELECT * FROM historical_price WHERE token_id=:tokenId")
    abstract fun selectAllByTokenId(tokenId: Long): Single<List<PricePoint>>

    @Query("SELECT * FROM historical_price WHERE id=:id")
    abstract fun selectById(id: Long): Single<PricePoint>

    @Query("SELECT * FROM historical_price WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): PricePoint

    @Insert
    abstract fun insertSynchronously(wallet: PricePoint): Long

    @Update
    abstract fun updateSynchronously(wallet: PricePoint): Int

    @Query("DELETE FROM historical_price WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
