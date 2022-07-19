package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.Columns
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class BlockChain(
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.SYMBOL)
    val symbol: String,
    @field:ColumnInfo(name = Columns.NODE_URL)
    val nodeUrl: String,
    @field:ColumnInfo(name = Columns.EXPLORER)
    val explorerUrl: String,
    @PrimaryKey
    @field:ColumnInfo(name = Columns.CHAIN_ID)
    val id: Long = 0L,
)
