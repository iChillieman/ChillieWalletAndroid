package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.PricePoint

@Dao
interface PricePointDao {
    @Query("SELECT * FROM historical_price")
    suspend fun selectAll(): List<PricePoint>

    @Query("SELECT * FROM historical_price WHERE token_id=:tokenId")
    suspend fun selectAllByTokenId(tokenId: String): List<PricePoint>

    @Query("SELECT * FROM historical_price WHERE id=:id")
    suspend fun selectById(id: Long): PricePoint

    @Insert
    suspend fun insert(price: PricePoint): Long

    @Update
    suspend fun update(price: PricePoint): Int

    @Query("DELETE FROM historical_price WHERE id=:id")
    suspend fun delete(id: Long): Int
}
