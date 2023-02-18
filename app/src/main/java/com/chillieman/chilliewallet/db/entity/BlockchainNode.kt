package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions.NODE_TABLE_NAME
import com.chillieman.chilliewallet.definitions.BlockchainDefinitions.NodeColumns

@Entity(tableName = NODE_TABLE_NAME)
class BlockchainNode(
    @field:ColumnInfo(name = NodeColumns.BLOCKCHAIN_ID)
    val blockchainId: Long,
    @field:ColumnInfo(name = NodeColumns.NODE_URL)
    val nodeUrl: String,
    @field:ColumnInfo(name = NodeColumns.LAST_PING)
    val lastPing: Long? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = NodeColumns.ID)
    val id: Long,
)
