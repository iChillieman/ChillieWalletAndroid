package com.chillieman.chilliewallet.definitions

object ChillieSettingDefinitions {
    const val TABLE_NAME = "settings"

    object Columns {
        const val SETTINGS_KEY = "settings_key"
        const val VALUE = "value"
    }

    object Keys {
        const val SELECTED_BLOCKCHAIN = "selected_blockchain"
        const val SELECTED_WALLET = "selected_wallet"
        const val NOTIFICATION_TRANSACTION = "is_notification_transaction"
        const val NOTIFICATION_BUY = "is_notification_buy"
        const val NOTIFICATION_SELL = "is_notification_sell"
        const val TIMEOUT = "is_timeout_enabled"
        const val TIMEOUT_LENGTH = "timeout_length"
    }

    object Defaults {
        const val NOTIFICATION_TRANSACTION = "true"
        const val NOTIFICATION_BUY = "true"
        const val NOTIFICATION_SELL = "true"
        const val TIMEOUT = "true"
        const val TIMEOUT_LENGTH = "10"
    }
}