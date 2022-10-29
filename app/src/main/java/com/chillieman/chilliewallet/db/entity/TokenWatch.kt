package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.TokenDefinitions.WATCH_TABLE_NAME
import com.chillieman.chilliewallet.definitions.TokenDefinitions.WatchColumns

@Entity(
    tableName = WATCH_TABLE_NAME,
    indices = [
        Index(WatchColumns.ID)
    ]
)
class TokenWatch(
    @field:ColumnInfo(name = WatchColumns.WALLET_ID)
    val walletId: Long,
    @field:ColumnInfo(name = WatchColumns.DEX_ID)
    val dexId: Long,
    @field:ColumnInfo(name = WatchColumns.TOKEN_ADDRESS)
    val tokenAddress: String,
    @field:ColumnInfo(name = WatchColumns.IS_WATCHING)
    val isWatching: Boolean,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = WatchColumns.ID)
    val id: Long = 0L,
) {
    fun copy(
        walletId: Long = this.walletId,
        dexId: Long = this.dexId,
        tokenAddress: String = this.tokenAddress,
        isWatching: Boolean = this.isWatching,
        id: Long = this.id
    ) = TokenWatch(walletId, dexId, tokenAddress, isWatching, id)
}