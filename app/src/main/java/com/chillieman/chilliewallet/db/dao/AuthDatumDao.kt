package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.AuthDatum

@Dao
interface AuthDatumDao {
    @Query("SELECT * FROM auth_datum")
    suspend fun selectAll(): AuthDatum

    @Query("SELECT * FROM auth_datum WHERE id=:id")
    suspend fun selectById(id: Long): AuthDatum

    @Insert
    suspend fun insert(datum: AuthDatum): Long

    @Update
    suspend fun update(datum: AuthDatum): Int

    @Query("DELETE FROM auth_datum WHERE id=:id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM auth_datum")
    suspend fun clear(): Int
}
