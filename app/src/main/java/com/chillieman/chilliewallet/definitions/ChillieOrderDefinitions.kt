package com.chillieman.chilliewallet.definitions

object ChillieOrderDefinitions {
    const val TABLE_NAME = "chillie_order"

    object Columns {
        const val ID = "id"
        const val TOKEN_ID = "token_id"
        const val WALLET_ID = "wallet_id"
        const val ORDER_STATE = "state"
        const val TIMESTAMP = "timestamp"
        const val STRATEGY_ID = "strategy_id"
        const val IS_REPEAT = "is_repeat"
    }

    const val STEP_TABLE_NAME = "chillie_order_step"

    object StepColumns {
        const val ID = "id"
        const val ACTION = "action"
        const val ACTION_VALUE = "value"
        const val STEP_STATE = "state"
        const val ORDER_ID = "order_id"
        const val TARGET_PRICE = "target_price"
        const val TARGET_PRICE_MOVEMENT = "target_price_percentage"
        const val STOP_LOSS_PRICE = "stop_loss_value"
        const val STOP_LOSS_PERCENTAGE = "stop_loss_percentage"
        const val CHILLIE_WALLET_FEE = "fee_in_wei"
        const val IS_FEE_PAID = "is_fee_paid"
    }
}
