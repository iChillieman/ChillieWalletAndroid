package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieSetting

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings")
    suspend fun selectAll(): List<ChillieSetting>

    @Query("SELECT * FROM settings WHERE settings_key=:key")
    suspend fun selectByKey(key: String): ChillieSetting

    @Insert
    suspend fun insert(wallet: ChillieSetting): Long

    @Update
    suspend fun update(wallet: ChillieSetting): Int

    @Query("DELETE FROM settings WHERE settings_key=:key")
    suspend fun delete(key: String): Int
}