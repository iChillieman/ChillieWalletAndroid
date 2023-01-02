package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.Authentication

@Dao
interface AuthDao {
    @Query("SELECT * FROM auth WHERE id=1")
    suspend fun select(): Authentication

    @Insert
    suspend fun insert(auth: Authentication): Long

    @Update
    suspend fun update(auth: Authentication): Int

    @Query("SELECT COUNT(*) FROM auth")
    suspend fun count(): Long

    @Query("DELETE FROM auth")
    suspend fun clear(): Int
}
