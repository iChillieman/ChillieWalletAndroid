package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieChainStep

@Dao
interface ChillieChainStepDao {
    @Query("SELECT * FROM chillie_chain_step")
    suspend fun selectAll(): List<ChillieChainStep>

    @Query("SELECT * FROM chillie_chain_step WHERE chillie_chain_id=:chillieChainId")
    suspend fun selectAllForChillieChain(chillieChainId: Long): List<ChillieChainStep>

    @Query("SELECT * FROM chillie_chain_step WHERE id=:id")
    suspend fun selectById(id: Long): ChillieChainStep

    @Insert
    suspend fun insert(step: ChillieChainStep): Long

    @Insert
    suspend fun insertAll(steps: List<ChillieChainStep>)

    @Update
    suspend fun update(step: ChillieChainStep): Int

    @Query("DELETE FROM chillie_chain_step WHERE id=:id")
    suspend fun delete(id: Long): Int
}
