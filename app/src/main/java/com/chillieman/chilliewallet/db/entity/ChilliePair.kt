package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChilliePairDefinitions.Columns
import com.chillieman.chilliewallet.definitions.ChilliePairDefinitions.TABLE_NAME
import java.math.BigInteger


@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID)
    ]
)
class ChilliePair(
    @field:ColumnInfo(name = Columns.BLOCKCHAIN_ID)
    val blockChainId: Long,
    @field:ColumnInfo(name = Columns.ADDRESS)
    val address: String,
    @field:ColumnInfo(name = Columns.TOKEN_0)
    val token0: String,
    @field:ColumnInfo(name = Columns.TOKEN_1)
    val token1: String,
    @field:ColumnInfo(name = Columns.RESERVE_0)
    val reserve0: BigInteger,
    @field:ColumnInfo(name = Columns.RESERVE_1)
    val reserve1: BigInteger,
    @field:PrimaryKey
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
) {
    fun copy(
        blockChainId: Long = this.blockChainId,
        address: String = this.address,
        token0: String = this.token0,
        token1: String = this.token1,
        reserve0: BigInteger = this.reserve0,
        reserve1: BigInteger = this.reserve1,
        key: Long = this.id
    ) = ChilliePair(blockChainId, address, token0, token1, reserve0, reserve1, key)
}