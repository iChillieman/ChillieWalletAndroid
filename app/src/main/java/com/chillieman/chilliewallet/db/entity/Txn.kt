package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

import com.chillieman.chilliewallet.definitions.TransactionDefinitions.TABLE_NAME
import com.chillieman.chilliewallet.definitions.TransactionDefinitions.Columns


@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID, unique = true)
    ]
)
class Txn (
    @field:ColumnInfo(name = Columns.TOKEN_ID)
    val tokenId : Long,
    @field:ColumnInfo(name = Columns.WALLET_ID)
    val walletId : Long,
    @field:ColumnInfo(name = Columns.TXN)
    val txn: String,
    @field:ColumnInfo(name = Columns.CONTENT)
    val content_json: String? = null,
    @field:ColumnInfo(name = Columns.IS_SUCCESS)
    val is_success: Boolean? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id : Long = 0L
)
