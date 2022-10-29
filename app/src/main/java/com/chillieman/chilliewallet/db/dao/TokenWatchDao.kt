package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.TokenWatch
import io.reactivex.Single

@Dao
abstract class TokenWatchDao {
    @Query("SELECT * FROM token_wallet")
    abstract fun selectAll(): Single<List<TokenWatch>>

    @Query("SELECT * FROM token_wallet WHERE wallet_id=:walletId")
    abstract fun selectAllByWalletId(walletId: Long): Single<List<TokenWatch>>

    @Query("SELECT * FROM token_wallet WHERE id=:id")
    abstract fun selectById(id: Long): Single<TokenWatch>

    @Query("SELECT * FROM token_wallet WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): TokenWatch

    @Insert
    abstract fun insert(watch: TokenWatch): Single<Long>

    @Update
    abstract fun update(watch: TokenWatch): Single<Int>

    @Query("DELETE FROM token_wallet WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}