package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.Columns
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.TABLE_NAME
import java.math.BigInteger

@Entity(tableName = TABLE_NAME)
class BlockChain(
    @PrimaryKey
    @field:ColumnInfo(name = Columns.CHAIN_ID)
    val id: Long,
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.SYMBOL)
    val symbol: String,
    @field:ColumnInfo(name = Columns.EXPLORER)
    val explorerUrl: String,
    @field:ColumnInfo(name = Columns.IS_TESTNET)
    val isTestnet: Boolean,
    @field:ColumnInfo(name = Columns.LOGO_URL)
    val logoUrl: String = "",
    @field:ColumnInfo(name = Columns.LAST_BLOCK_SYNC)
    val lastBlockSynced: BigInteger? = null
)
