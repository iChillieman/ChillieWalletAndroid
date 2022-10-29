package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions

@Entity(tableName = AuthenticationDefinitions.DATUM_TABLE_NAME)
class AuthDatum(
    @field:ColumnInfo(name = AuthenticationDefinitions.DatumColumns.PAYLOAD)
    val payload: ByteArray,
    @field:ColumnInfo(name = AuthenticationDefinitions.DatumColumns.IV)
    val iv: ByteArray,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = AuthenticationDefinitions.DatumColumns.ID)
    val id: Long = 0L,
) {
    fun copy(
        payload: ByteArray = this.payload,
        iv: ByteArray = this.iv,
        id: Long = this.id
    ): AuthDatum {
        return AuthDatum(payload, iv, id)
    }
}
