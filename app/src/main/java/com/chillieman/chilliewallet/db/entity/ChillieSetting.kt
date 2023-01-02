package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieSettingDefinitions.Columns
import com.chillieman.chilliewallet.definitions.ChillieSettingDefinitions.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(Columns.SETTINGS_KEY, unique = true)
    ]
)
class ChillieSetting(
    @field:PrimaryKey
    @field:ColumnInfo(name = Columns.SETTINGS_KEY)
    val key: String,
    @field:ColumnInfo(name = Columns.VALUE)
    val value: String?
)