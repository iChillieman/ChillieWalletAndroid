package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.ChillieSetting
import io.reactivex.Single

@Dao
abstract class SettingsDao {
    @Query("SELECT * FROM settings")
    abstract fun selectAll(): Single<List<ChillieSetting>>

    @Query("SELECT * FROM settings WHERE settings_key=:key")
    abstract fun selectByKey(key: String): Single<ChillieSetting>

    @Query("SELECT * FROM settings WHERE settings_key=:key")
    abstract fun selectByKeySynchronously(key: String): ChillieSetting

    @Insert
    abstract fun insert(wallet: ChillieSetting): Single<Long>

    @Update
    abstract fun update(wallet: ChillieSetting): Single<Int>

    @Query("DELETE FROM settings WHERE settings_key=:key")
    abstract fun delete(key: String): Single<Int>
}