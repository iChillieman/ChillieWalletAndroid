package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieOrderDefinitions.StepColumns
import com.chillieman.chilliewallet.definitions.ChillieOrderDefinitions.STEP_TABLE_NAME
import com.chillieman.chilliewallet.model.ChillieOrderAction
import com.chillieman.chilliewallet.model.ChillieOrderState
import java.math.BigInteger

@Entity(
    tableName = STEP_TABLE_NAME,
    indices = [
        Index(StepColumns.ID, unique = true)
    ]
)
class ChillieOrderStep(
    @field:ColumnInfo(name = StepColumns.ORDER_ID)
    val orderId: Long,
    @field:ColumnInfo(name = StepColumns.ACTION)
    val action: ChillieOrderAction,
    @field:ColumnInfo(name = StepColumns.ACTION_VALUE)
    val value: BigInteger,
    @field:ColumnInfo(name = StepColumns.STEP_STATE)
    val state: ChillieOrderState,
    @field:ColumnInfo(name = StepColumns.TARGET_PRICE)
    val targetPriceInWei: BigInteger,
    @field:ColumnInfo(name = StepColumns.STOP_LOSS_PRICE)
    val stopLossPriceInWei: BigInteger? = null,
    @field:ColumnInfo(name = StepColumns.STOP_LOSS_PERCENTAGE)
    val stopLossPercentage: Int? = null,
    @field:ColumnInfo(name = StepColumns.TARGET_PRICE_MOVEMENT)
    val targetMovementPercentage: Int? = null,
    @field:ColumnInfo(name = StepColumns.CHILLIE_WALLET_FEE)
    val feeInWei: BigInteger? = null,
    @field:ColumnInfo(name = StepColumns.IS_FEE_PAID)
    val isFeePaid: Boolean? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = StepColumns.ID)
    val id: Long = 0L
)
