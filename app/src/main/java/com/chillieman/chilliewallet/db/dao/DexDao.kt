package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.Dex
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class DexDao {
    @Query("SELECT * FROM dex")
    abstract fun selectAll(): Single<List<Dex>>

    @Query("SELECT * FROM dex WHERE blockchain_id=:blockChainId")
    abstract fun selectAllByChainId(blockChainId: Long): Single<List<Dex>>

    @Query("SELECT * FROM dex WHERE id=:id")
    abstract fun selectById(id: Long): Single<Dex>

    @Query("SELECT * FROM dex WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): Dex

    @Query("SELECT * FROM dex WHERE blockchain_id=:blockChainId AND address_router=:address")
    abstract fun selectByChainIdAndRouterAddress(blockChainId: Long, address: String): Single<Dex>

    @Insert
    abstract fun insert(dex: Dex): Single<Long>

    @Insert
    abstract fun insertAll(dexs: List<Dex>): Completable

    @Update
    abstract fun update(dex: Dex): Single<Int>

    @Query("DELETE FROM dex WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}