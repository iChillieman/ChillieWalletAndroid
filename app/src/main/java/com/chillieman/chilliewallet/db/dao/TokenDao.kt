package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.Token

@Dao
interface TokenDao {
    @Query("SELECT * FROM token")
    suspend fun selectAll(): List<Token>

    @Query("SELECT * FROM token WHERE id=:id")
    suspend fun selectById(id: Long): Token

    @Query("SELECT * FROM token WHERE address=:address AND blockchain_id=:blockchainId")
    suspend fun selectByAddressAndBlockchainId(address: String, blockchainId: Long): Token

    @Query("SELECT COUNT(*) FROM token WHERE address=:address AND blockchain_id=:blockchainId")
    suspend fun countByAddressAndBlockchainId(address: String, blockchainId: Long): Int

    @Insert
    suspend fun insert(token: Token): Long

    @Insert
    suspend fun insertAll(tokens: List<Token>)

    @Update
    suspend fun update(token: Token): Int

    @Query("DELETE FROM token WHERE id=:id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM token")
    suspend fun clear(): Int
}
