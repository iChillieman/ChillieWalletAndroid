package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieOrderDefinitions.Columns
import com.chillieman.chilliewallet.definitions.ChillieOrderDefinitions.TABLE_NAME
import com.chillieman.chilliewallet.model.ChillieOrderState

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]
)
class ChillieOrder(
    @field:ColumnInfo(name = Columns.TOKEN_ADDRESS)
    val tokenAddress: String,
    @field:ColumnInfo(name = Columns.WALLET_ID)
    val chillieWalletId: Long,
    @field:ColumnInfo(name = Columns.DEX_ID)
    val dexId: Long,
    @field:ColumnInfo(name = Columns.ORDER_STATE)
    val state: ChillieOrderState,
    @field:ColumnInfo(name = Columns.TIMESTAMP)
    val timestamp: Long,
    @field:ColumnInfo(name = Columns.STRATEGY_ID)
    val chillieChainId: Long? = null,
    @field:ColumnInfo(name = Columns.IS_REPEAT)
    val isRepeat: Boolean? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
) {
    fun copy(
        tokenAddress: String = this.tokenAddress,
        chillieWalletId: Long = this.chillieWalletId,
        dexId: Long = this.dexId,
        state: ChillieOrderState = this.state,
        timestamp: Long = this.timestamp,
        chillieChainId: Long? = this.chillieChainId,
        isRepeat: Boolean? = this.isRepeat,
        id: Long = this.id
    ) = ChillieOrder(
        tokenAddress,
        chillieWalletId,
        dexId,
        state,
        timestamp,
        chillieChainId,
        isRepeat,
        id
    )
}
