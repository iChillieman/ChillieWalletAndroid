package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.Token
import io.reactivex.Single

@Dao
abstract class TokenDao {
    @Query("SELECT * FROM token")
    abstract fun selectAll(): Single<List<Token>>

    @Query("SELECT * FROM token WHERE id=:id")
    abstract fun selectById(id: Long): Single<Token>

    @Query("SELECT * FROM token WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): Token

    @Insert
    abstract fun insert(token: Token): Long

    @Insert
    abstract fun insertAll(tokens: List<Token>)

    @Update
    abstract fun update(token: Token): Int

    @Query("SELECT COUNT(*) FROM token")
    abstract fun count(): Single<Long>

    @Query("DELETE FROM token WHERE id=:id")
    abstract fun delete(id: Long): Int
}
