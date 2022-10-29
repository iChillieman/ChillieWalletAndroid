package com.chillieman.chilliewallet.definitions

object ChillieChainDefinitions {
    const val TABLE_NAME = "chillie_chain"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val IS_REPEATABLE = "is_repeatable"
    }

    const val STEP_TABLE_NAME = "chillie_chain_step"

    object StepColumns {
        const val ID = "id"
        const val CHILLIE_CHAIN_ID = "chillie_chain_id"
        const val ACTION = "action"
        const val ACTION_VALUE = "value"
        const val DESIRED_MOVEMENT_PERCENTAGE = "desired_movement_percent"
        const val STOP_LOSS_PERCENTAGE = "stop_loss_percent"
        const val STATIC_TARGET_PRICE = "static_price"
    }
}
