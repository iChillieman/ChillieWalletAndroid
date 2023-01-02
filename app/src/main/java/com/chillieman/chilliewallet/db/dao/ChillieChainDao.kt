package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieChain

@Dao
interface ChillieChainDao {
    @Query("SELECT * FROM chillie_chain")
    suspend fun selectAll(): List<ChillieChain>

    @Query("SELECT * FROM chillie_chain WHERE id=:id")
    suspend fun selectById(id: Long): ChillieChain

    @Insert
    suspend fun insert(chillieChain: ChillieChain): Long

    @Update
    suspend fun update(chillieChain: ChillieChain): Int

    @Query("DELETE FROM chillie_chain WHERE id=:id")
    suspend fun delete(id: Long): Int
}
