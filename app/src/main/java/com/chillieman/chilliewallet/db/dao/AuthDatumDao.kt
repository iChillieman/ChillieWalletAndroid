package com.chillieman.chilliewallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chillieman.chilliewallet.db.entity.AuthDatum
import io.reactivex.Single

@Dao
abstract class AuthDatumDao {
    @Query("SELECT * FROM auth_datum")
    abstract fun selectAll(): Single<AuthDatum>

    @Query("SELECT * FROM auth_datum WHERE id=:id")
    abstract fun selectById(id: Long): Single<AuthDatum>

    @Query("SELECT * FROM auth_datum WHERE id=:id")
    abstract fun selectByIdSynchronously(id: Long): AuthDatum

    @Insert
    abstract fun insert(datum: AuthDatum): Single<Long>

    @Update
    abstract fun update(datum: AuthDatum): Single<Int>

    @Query("DELETE FROM auth")
    abstract fun delete(): Single<Int>
}
