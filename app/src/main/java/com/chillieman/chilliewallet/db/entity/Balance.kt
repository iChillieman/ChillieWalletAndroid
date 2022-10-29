package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BalanceDefinitions.TABLE_NAME
import com.chillieman.chilliewallet.definitions.BalanceDefinitions.Columns
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
    @field:ColumnInfo(name = Columns.TOKEN_ADDRESS)
    val tokenId: Long,
    @field:ColumnInfo(name = Columns.TOTAL_BALANCE)
    val balance: BigInteger,
    @field:ColumnInfo(name = Columns.TOKENS_IN_ORDER)
    val balanceInOrders: BigInteger,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
) {
    fun copy(
        walletId: Long = this.walletId,
        tokenId: Long = this.tokenId,
        balance: BigInteger = this.balance,
        balanceInOrders: BigInteger = this.balanceInOrders,
        id: Long = this.id
    ) = Balance(walletId, tokenId, balance, balanceInOrders, id)
}
