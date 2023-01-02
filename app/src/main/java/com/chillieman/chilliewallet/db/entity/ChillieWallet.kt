package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieWalletDefinitions.Columns
import com.chillieman.chilliewallet.definitions.ChillieWalletDefinitions.TABLE_NAME


@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]
)
data class ChillieWallet(
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.SEED_ID)
    val seedId: Long,
    @field:ColumnInfo(name = Columns.ADDRESS)
    val address: String,
    @field:ColumnInfo(name = Columns.IS_CONFIRMED)
    val isConfirmed: Boolean = false,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L,
)
