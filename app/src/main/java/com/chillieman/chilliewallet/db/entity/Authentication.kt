package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.Columns
import com.chillieman.chilliewallet.definitions.AuthenticationDefinitions.TABLE_NAME


@Entity(tableName = TABLE_NAME)
class Authentication(
    @field:PrimaryKey
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 1L,
    @field:ColumnInfo(name = Columns.PIN)
    val pinId: Long,
    @field:ColumnInfo(name = Columns.WALLET_PASSWORD)
    val walletPasswordId: Long,
    @field:ColumnInfo(name = Columns.PASSWORD)
    val userPasswordId: Long?
)
