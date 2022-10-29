package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.BlockChain
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class BlockChainDao {
    @Query("SELECT * FROM block_chain")
    abstract fun selectAll(): Single<List<BlockChain>>

    @Query("SELECT * FROM block_chain WHERE id=:id")
    abstract fun selectById(id: Long): Single<BlockChain>

    @Query("SELECT * FROM block_chain WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): BlockChain

    @Insert
    abstract fun insert(blockChain: BlockChain): Single<Long>

    @Insert
    abstract fun insertAll(blockChains: List<BlockChain>): Completable

    @Update
    abstract fun update(blockChain: BlockChain): Single<Int>

    @Query("DELETE FROM block_chain WHERE id=:id")
    abstract fun delete(id: Long): Int
}
