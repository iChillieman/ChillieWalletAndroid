package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.Dex

@Dao
interface DexDao {
    @Query("SELECT * FROM dex")
    suspend fun selectAll(): List<Dex>

    @Query("SELECT * FROM dex WHERE blockchain_id=:blockChainId")
    suspend fun selectAllByChainId(blockChainId: Long): List<Dex>

    @Query("SELECT * FROM dex WHERE id=:id")
    suspend fun selectById(id: Long): Dex

    @Query("SELECT * FROM dex WHERE blockchain_id=:blockChainId AND address_router=:address")
    suspend fun selectByChainIdAndRouterAddress(blockChainId: Long, address: String): Dex

    @Insert
    suspend fun insert(dex: Dex): Long

    @Insert
    suspend fun insertAll(dexs: List<Dex>)

    @Update
    suspend fun update(dex: Dex): Int

    @Query("DELETE FROM dex WHERE id=:id")
    suspend fun delete(id: Long): Int
}