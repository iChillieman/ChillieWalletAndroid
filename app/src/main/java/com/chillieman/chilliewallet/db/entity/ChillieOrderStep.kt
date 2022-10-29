package com.chillieman.chilliewallet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chillieman.chilliewallet.definitions.ChillieOrderDefinitions.StepColumns
import com.chillieman.chilliewallet.definitions.ChillieOrderDefinitions.STEP_TABLE_NAME
import com.chillieman.chilliewallet.model.ChillieOrderAction
import com.chillieman.chilliewallet.model.ChillieOrderStepState
import java.math.BigInteger

@Entity(
    tableName = STEP_TABLE_NAME,
    indices = [
        Index(StepColumns.ID, unique = true)
    ]
)
class ChillieOrderStep(
    @field:ColumnInfo(name = StepColumns.ORDER_ID)
    val chillieOrderId: Long,
    @field:ColumnInfo(name = StepColumns.ACTION)
    val action: ChillieOrderAction,
    @field:ColumnInfo(name = StepColumns.ACTION_VALUE)
    val value: BigInteger,
    @field:ColumnInfo(name = StepColumns.STARTING_PRICE)
    val startingPriceInWei: BigInteger,
    @field:ColumnInfo(name = StepColumns.TARGET_PRICE)
    val targetPriceInWei: BigInteger,
    @field:ColumnInfo(name = StepColumns.TARGET_PRICE_MOVEMENT)
    val targetMovementPercentage: Int,
    @field:ColumnInfo(name = StepColumns.STEP_STATE)
    val state: ChillieOrderStepState = ChillieOrderStepState.PENDING,
    @field:ColumnInfo(name = StepColumns.STOP_LOSS_PRICE)
    val stopLossPriceInWei: BigInteger? = null,
    @field:ColumnInfo(name = StepColumns.STOP_LOSS_PERCENTAGE)
    val stopLossPercentage: Int? = null,
    @field:ColumnInfo(name = StepColumns.TXN_ID)
    val txnId: Long? = null,
    @field:ColumnInfo(name = StepColumns.CHILLIE_WALLET_FEE)
    val feeInWei: BigInteger? = null,
    @field:ColumnInfo(name = StepColumns.IS_FEE_PAID)
    val isFeePaid: Boolean? = null,
    @field:ColumnInfo(name = StepColumns.FEE_TXN_ID)
    val txnFeeId: Long? = null,
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = StepColumns.ID)
    val id: Long = 0L
) {
    fun copy(
        chillieOrderId: Long = this.chillieOrderId,
        action: ChillieOrderAction = this.action,
        value: BigInteger = this.value,
        startingPriceInWei: BigInteger = this.startingPriceInWei,
        targetPriceInWei: BigInteger = this.targetPriceInWei,
        targetMovementPercentage: Int = this.targetMovementPercentage,
        state: ChillieOrderStepState = this.state,
        stopLossPriceInWei: BigInteger? = this.stopLossPriceInWei,
        stopLossPercentage: Int? = this.stopLossPercentage,
        txnId: Long? = this.txnId,
        feeInWei: BigInteger? = this.feeInWei,
        isFeePaid: Boolean? = this.isFeePaid,
        txnFeeId: Long? = this.txnFeeId,
        id: Long = this.id
    ) = ChillieOrderStep(
        chillieOrderId,
        action,
        value,
        startingPriceInWei,
        targetPriceInWei,
        targetMovementPercentage,
        state,
        stopLossPriceInWei,
        stopLossPercentage,
        txnId,
        feeInWei,
        isFeePaid,
        txnFeeId,
        id
    )
}
