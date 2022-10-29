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
    @field:ColumnInfo(name = Columns.NODE_URL)
    val nodeUrl: String,
    @field:ColumnInfo(name = Columns.EXPLORER)
    val explorerUrl: String,
    @field:ColumnInfo(name = Columns.LOGO_URL)
    val logoUrl: String,
    @field:ColumnInfo(name = Columns.LAST_BLOCK_SYNC)
    val lastBlockSynced: BigInteger? = null, //TODO: CHILLIE - SEE IF THIS SHOULD BE A LONG / INT
) {
    fun copy(
        id: Long = this.id,
        name: String = this.name,
        symbol: String = this.symbol,
        nodeUrl: String = this.nodeUrl,
        explorerUrl: String = this.explorerUrl,
        logoUrl: String = this.logoUrl,
        lastBlockSynced: BigInteger? = this.lastBlockSynced
    ) = BlockChain(id, name, symbol, nodeUrl, explorerUrl, logoUrl, lastBlockSynced)
}
