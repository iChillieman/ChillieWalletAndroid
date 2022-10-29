package com.chillieman.chilliewallet.definitions

object ChillieSettingDefinitions {
    const val TABLE_NAME = "settings"

    object Columns {
        const val SETTINGS_KEY = "settings_key"
        const val VALUE = "value"
    }

    object Keys {
        const val SETTING_SELECTED_BLOCKCHAIN = "selected_blockchain"
        const val SETTING_SELECTED_WALLET = "selected_wallet"
        const val SETTING_NOTIFICATION_TRANSACTION = "is_notification_transaction"
        const val SETTING_NOTIFICATION_BUY = "is_notification_buy"
        const val SETTING_NOTIFICATION_SELL = "is_notification_sell"
        const val SETTING_TIMEOUT = "is_timeout_enabled"
        const val SETTING_TIMEOUT_LENGTH = "timeout_length"
    }

    object Defaults {
        const val SETTING_NOTIFICATION_TRANSACTION = "true"
        const val SETTING_NOTIFICATION_BUY = "true"
        const val SETTING_NOTIFICATION_SELL = "true"
        const val SETTING_TIMEOUT = "true"
        const val SETTING_TIMEOUT_LENGTH = "10"
    }
}