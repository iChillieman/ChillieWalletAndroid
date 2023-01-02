package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.NODE_TABLE_NAME
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions.NodeColumns

@Entity(tableName = NODE_TABLE_NAME)
class BlockChainNode(
    @field:ColumnInfo(name = NodeColumns.BLOCKCHAIN_ID)
    val blockChainId: Long,
    @field:ColumnInfo(name = NodeColumns.NODE_URL)
    val nodeUrl: String,
    @field:ColumnInfo(name = NodeColumns.LAST_PING)
    val lastPing: Long? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = NodeColumns.ID)
    val id: Long,
)
