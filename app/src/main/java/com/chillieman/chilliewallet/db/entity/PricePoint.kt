package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.PricePointDefinitions.Columns
import com.chillieman.chilliewallet.definitions.PricePointDefinitions.TABLE_NAME
import java.math.BigInteger

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]
)
class PricePoint(
    @field:ColumnInfo(name = Columns.DEX_ID)
    val dexId: Long,
    @field:ColumnInfo(name = Columns.TOKEN_ID)
    val tokenId: Long,
    @field:ColumnInfo(name = Columns.PRICE_IN_ETH)
    val priceInEth: BigInteger,
    @field:ColumnInfo(name = Columns.PRICE_IN_TOKEN)
    val ethPriceByToken: BigInteger,
    @field:ColumnInfo(name = Columns.PRECISION)
    val precision: Int,
    @field:ColumnInfo(name = Columns.CURRENT_BLOCK)
    val currentBlock: BigInteger,
    @field:ColumnInfo(name = Columns.TIMESTAMP)
    val timestamp: Long,
    @field:ColumnInfo(name = Columns.IS_GLOBAL_WATCHER)
    val isGlobalPriceWatcher: Boolean,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
)
