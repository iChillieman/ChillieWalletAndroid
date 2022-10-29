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
    @field:ColumnInfo(name = Columns.BLOCKCHAIN_ID)
    val blockChainId: Long,
    @field:ColumnInfo(name = Columns.ADDRESS)
    val address: String,
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.DECIMALS)
    val decimals: Int,
    @field:ColumnInfo(name = Columns.SYMBOL)
    val symbol: String,
    @field:ColumnInfo(name = Columns.LOGO_URL)
    val logoUrl: String? = null,
    @field:ColumnInfo(name = Columns.TAX_BUY)
    val taxBuy: Int? = null,
    @field:ColumnInfo(name = Columns.TAX_SELL)
    val taxSell: Int? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L,
) {
    fun copy(
        blockChainId: Long = this.blockChainId,
        address: String = this.address,
        name: String = this.name,
        decimals: Int = this.decimals,
        symbol: String = this.symbol,
        logoUrl: String? = this.logoUrl,
        taxBuy: Int? = this.taxBuy,
        taxSell: Int? = this.taxSell,
        id: Long = this.id
    ) = Token(blockChainId, address, name, decimals, symbol, logoUrl, taxBuy, taxSell, id)
}
