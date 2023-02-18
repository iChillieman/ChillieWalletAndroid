package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions.Columns
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions.TABLE_NAME
import java.math.BigInteger

@Entity(tableName = TABLE_NAME)
class Blockchain(
    @PrimaryKey
    @field:ColumnInfo(name = Columns.BLOCKCHAIN_ID)
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
    @field:ColumnInfo(name = Columns.SELECTED_NODE)
    val selectedNodeId: Long? = null,
    @field:ColumnInfo(name = Columns.LAST_BLOCK_SYNC)
    val lastBlockSynced: BigInteger? = null
)
