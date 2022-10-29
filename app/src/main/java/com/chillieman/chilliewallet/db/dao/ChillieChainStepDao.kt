package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieChainStep
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ChillieChainStepDao {
    @Query("SELECT * FROM chillie_chain_step")
    abstract fun selectAll(): Single<List<ChillieChainStep>>

    @Query("SELECT * FROM chillie_chain_step WHERE chillie_chain_id=:chillieChainId")
    abstract fun selectAllForChillieChain(chillieChainId: Long): Single<List<ChillieChainStep>>

    @Query("SELECT * FROM chillie_chain_step WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieChainStep>

    @Query("SELECT * FROM chillie_chain_step WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieChainStep

    @Insert
    abstract fun insert(step: ChillieChainStep): Single<Long>

    @Insert
    abstract fun insertAll(steps: List<ChillieChainStep>): Completable

    @Update
    abstract fun update(step: ChillieChainStep): Single<Int>

    @Query("DELETE FROM chillie_chain_step WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}
