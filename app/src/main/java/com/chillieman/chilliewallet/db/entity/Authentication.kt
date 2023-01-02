package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.Columns
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.DEFAULT_ID
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Authentication(
    @field:PrimaryKey
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = DEFAULT_ID,
    @field:ColumnInfo(name = Columns.PIN)
    val pinId: Long,
    @field:ColumnInfo(name = Columns.PASSWORD)
    val passwordId: Long
)
