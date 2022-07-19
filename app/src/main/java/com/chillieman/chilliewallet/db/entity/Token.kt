package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.TokenDefinitions.Columns
import com.chillieman.chilliewallet.definitions.TokenDefinitions.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]
)
class Token(
    @field:ColumnInfo(name = Columns.CHAIN_ID)
    val chainId: Int,
    @field:ColumnInfo(name = Columns.ADDRESS)
    val address: String,
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.DECIMALS)
    val decimals: Int,
    @field:ColumnInfo(name = Columns.TAX_BUY)
    val taxBuy: Int? = null,
    @field:ColumnInfo(name = Columns.TAX_SELL)
    val taxSell: Int? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L,
)
