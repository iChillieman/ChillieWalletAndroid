package com.chillieman.chilliewallet.db.dao

import androidx.room.*
import com.chillieman.chilliewallet.db.entity.Authentication
import io.reactivex.Single

@Dao
abstract class AuthDao {
    @Query("SELECT * FROM auth WHERE id=1")
    abstract fun select(): Single<Authentication>

    @Insert
    abstract fun insert(auth: Authentication): Single<Long>

    @Update
    abstract fun update(auth: Authentication): Single<Int>

    @Query("DELETE FROM auth")
    abstract fun delete(): Single<Int>
}
