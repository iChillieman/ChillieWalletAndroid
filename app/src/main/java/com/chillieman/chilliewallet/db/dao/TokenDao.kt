package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.Token
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class TokenDao {
    @Query("SELECT * FROM token")
    abstract fun selectAll(): Single<List<Token>>

    @Query("SELECT * FROM token WHERE id=:id")
    abstract fun selectById(id: Long): Single<Token>

    @Query("SELECT * FROM token WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): Token

    @Query("SELECT * FROM token WHERE address=:tokenAddress")
    abstract fun selectByAddress(tokenAddress: String): Single<Token>

    @Query("SELECT * FROM token WHERE address=:tokenAddress")
    abstract fun selectByAddressSynchronously(tokenAddress: String): Token

    @Insert
    abstract fun insert(token: Token): Single<Long>

    @Insert
    abstract fun insertAll(tokens: List<Token>): Completable

    @Update
    abstract fun update(token: Token): Single<Int>

    @Query("DELETE FROM token WHERE id=:id")
    abstract fun delete(id: Long): Single<Int>
}
