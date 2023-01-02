package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.DexDefinitions.Columns
import com.chillieman.chilliewallet.definitions.DexDefinitions.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID)
    ]
)
data class Dex(
    @field:ColumnInfo(name = Columns.BLOCKCHAIN_ID)
    val blockChainId: Long,
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.ADDRESS_ROUTER)
    val addressRouter: String,
    @field:ColumnInfo(name = Columns.ADDRESS_FACTORY)
    val addressFactory: String,
    @field:ColumnInfo(name = Columns.LOGO_URL)
    val logoUrl: String? = null,
    @field:ColumnInfo(name = Columns.TAX_RATE_BUY)
    val taxRateBuy: Double? = null,
    @field:ColumnInfo(name = Columns.TAX_RATE_SELL)
    val taxRateSell: Double? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
)