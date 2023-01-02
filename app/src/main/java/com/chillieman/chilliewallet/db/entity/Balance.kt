package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BalanceDefinitions.Columns
import com.chillieman.chilliewallet.definitions.BalanceDefinitions.TABLE_NAME
import java.math.BigInteger

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]

)
class Balance(
    @field:ColumnInfo(name = Columns.WALLET_ID)
    val walletId: Long,
    @field:ColumnInfo(name = Columns.TOKEN_ID)
    val tokenId: Long,
    @field:ColumnInfo(name = Columns.TOTAL_BALANCE)
    val balance: BigInteger,
    @field:ColumnInfo(name = Columns.TOKENS_IN_ORDER)
    val balanceInOrders: BigInteger,
    @field:ColumnInfo(name = Columns.VALUE_IN_WEI)
    val valueInEthWei: BigInteger,
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
