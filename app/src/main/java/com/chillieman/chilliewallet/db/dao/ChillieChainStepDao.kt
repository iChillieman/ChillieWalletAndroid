package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieChainStep
import io.reactivex.Single

@Dao
abstract class ChillieChainStepDao {
    @Query("SELECT * FROM chillie_chain_step")
    abstract fun selectAll(): Single<List<ChillieChainStep>>

    @Query("SELECT * FROM chillie_chain_step WHERE chillie_chain_id=:chainId")
    abstract fun selectAllForChain(chainId: Long): Single<List<ChillieChainStep>>

    @Query("SELECT * FROM chillie_chain_step WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieChainStep>

    @Query("SELECT * FROM chillie_chain_step WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieChainStep

    @Insert
    abstract fun insertSynchronously(wallet: ChillieChainStep): Long

    @Update
    abstract fun updateSynchronously(wallet: ChillieChainStep): Int

    @Query("DELETE FROM chillie_chain_step WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
