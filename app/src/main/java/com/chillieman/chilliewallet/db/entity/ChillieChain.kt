package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieChainDefinitions.Columns
import com.chillieman.chilliewallet.definitions.ChillieChainDefinitions.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.ID)
    ]
)
class ChillieChain(
    @field:ColumnInfo(name = Columns.NAME)
    val name: String,
    @field:ColumnInfo(name = Columns.IS_REPEATABLE)
    val isRepeatable: Boolean,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = Columns.ID)
    val id: Long = 0L
) {
    fun copy(
        name: String = this.name,
        isRepeatable: Boolean = this.isRepeatable,
        id: Long = this.id
    ) = ChillieChain(name, isRepeatable, id)
}
