package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieChain
import io.reactivex.Single

@Dao
abstract class ChillieChainDao {
    @Query("SELECT * FROM chillie_chain")
    abstract fun selectAll(): Single<List<ChillieChain>>

    @Query("SELECT * FROM chillie_chain WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChillieChain>

    @Query("SELECT * FROM chillie_chain WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChillieChain

    @Insert
    abstract fun insertSynchronously(wallet: ChillieChain): Long

    @Update
    abstract fun updateSynchronously(wallet: ChillieChain): Int

    @Query("SELECT COUNT(*) FROM chillie_chain")
    abstract fun count(): Single<Long>

    @Query("DELETE FROM chillie_chain WHERE id=:id")
    abstract fun deleteSynchronously(id: Long): Int
}
