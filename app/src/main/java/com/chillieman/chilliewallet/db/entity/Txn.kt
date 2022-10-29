package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.TransactionDefinitions.Columns
import com.chillieman.chilliewallet.definitions.TransactionDefinitions.TABLE_NAME


@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]
)
class Txn(
    @field:ColumnInfo(name = Columns.BLOCKCHAIN_ID)
    val blockchainId: Long,
    @field:ColumnInfo(name = Columns.TOKEN_ADDRESS)
    val tokenAddress: String,
    @field:ColumnInfo(name = Columns.WALLET_ID)
    val walletId: Long,
    @field:ColumnInfo(name = Columns.TXN)
    val txn: String,
    @field:ColumnInfo(name = Columns.CONTENT)
    val contentJson: String? = null,
    @field:ColumnInfo(name = Columns.IS_SUCCESS)
    val isSuccess: Boolean? = null,
    @field:ColumnInfo(name = Columns.TIMESTAMP)
    val timestamp: Long? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
) {
    fun copy(
        blockchainId: Long = this.blockchainId,
        tokenAddress: String = this.tokenAddress,
        walletId: Long = this.walletId,
        txn: String = this.txn,
        contentJson: String? = this.contentJson,
        isSuccess: Boolean? = this.isSuccess,
        timestamp: Long? = this.timestamp,
        id: Long = this.id
    ) = Txn(blockchainId, tokenAddress, walletId, txn, contentJson, isSuccess, timestamp, id)
}
