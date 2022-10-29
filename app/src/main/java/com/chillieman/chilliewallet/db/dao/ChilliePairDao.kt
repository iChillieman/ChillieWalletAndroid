package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChilliePair
import io.reactivex.Single

@Dao
abstract class ChilliePairDao {
    @Query("SELECT * FROM pairs")
    abstract fun selectAll(): Single<List<ChilliePair>>

    @Query("SELECT * FROM pairs WHERE blockchain_id=:blockChainId")
    abstract fun selectAllByChainId(blockChainId: Long): Single<List<ChilliePair>>

    @Query("SELECT * FROM pairs WHERE id=:id")
    abstract fun selectById(id: Long): Single<ChilliePair>

    @Query("SELECT * FROM pairs WHERE token0=:tokenAddress OR token1=:tokenAddress" )
    abstract fun selectByTokenAddress(tokenAddress: String): Single<ChilliePair>

    @Query("SELECT * FROM pairs WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): ChilliePair

    @Insert
    abstract fun insert(pair: ChilliePair): Single<Long>

    @Update
    abstract fun update(pair: ChilliePair): Single<Int>

    @Query("DELETE FROM pairs WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}