package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieChainDefinitions.STEP_TABLE_NAME
import com.chillieman.chilliewallet.definitions.ChillieChainDefinitions.StepColumns
import com.chillieman.chilliewallet.model.ChillieOrderAction
import java.math.BigInteger

@Entity(
    tableName = STEP_TABLE_NAME,
    indices = [
        Index(StepColumns.ID)
    ]
)
class ChillieChainStep(
    @field:ColumnInfo(name = StepColumns.CHILLIE_CHAIN_ID)
    val chillieChainId: Long,
    @field:ColumnInfo(name = StepColumns.ACTION)
    val action: ChillieOrderAction,
    @field:ColumnInfo(name = StepColumns.ACTION_VALUE)
    val value: BigInteger,
    @field:ColumnInfo(name = StepColumns.DESIRED_MOVEMENT_PERCENTAGE)
    val desiredMovementPercent: Int? = null,
    @field:ColumnInfo(name = StepColumns.STOP_LOSS_PERCENTAGE)
    val stopLossPercentage: Int? = null,
    @field:ColumnInfo(name = StepColumns.STATIC_TARGET_PRICE)
    val specificEthPrice: BigInteger? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = StepColumns.ID)
    val id: Long = 0L
) {
    fun copy(
        chillieChainId: Long = this.chillieChainId,
        action: ChillieOrderAction = this.action,
        value: BigInteger = this.value,
        desiredMovementPercent: Int? = this.desiredMovementPercent,
        stopLossPercentage: Int? = this.stopLossPercentage,
        specificEthPrice: BigInteger? = this.specificEthPrice,
        id: Long = this.id
    ) = ChillieChainStep(
        chillieChainId,
        action,
        value,
        desiredMovementPercent,
        stopLossPercentage,
        specificEthPrice,
        id
    )
}
