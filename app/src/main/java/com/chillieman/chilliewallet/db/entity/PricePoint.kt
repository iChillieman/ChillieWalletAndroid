package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.PricePointDefinitions.Columns
import com.chillieman.chilliewallet.definitions.PricePointDefinitions.TABLE_NAME
import java.math.BigInteger


/**
 * NOTE - If a price point has a DEX_ID and TOKEN_ID of NOT
 */
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
    val priceInWei: BigInteger, // How many ETH can you get with 1 Token? (In WEI)
    @field:ColumnInfo(name = Columns.PRICE_IN_TOKEN)
    val priceInTokens: BigInteger, // How many tokens can you get with 1 eth?
    @field:ColumnInfo(name = Columns.TIMESTAMP)
    val timestamp: Long,
    @field:ColumnInfo(name = Columns.IS_GLOBAL_WATCHER)
    val isGlobalPriceWatcher: Boolean,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
)
