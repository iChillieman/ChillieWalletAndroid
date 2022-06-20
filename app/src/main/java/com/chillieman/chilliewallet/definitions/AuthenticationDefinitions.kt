package com.chillieman.chilliewallet.definitions

object AuthenticationDefinitions {
    const val TABLE_NAME = "auth"
    object Columns {
        const val ID = "id"
        const val PIN = "pin"
        const val PASSWORD = "pw"
        const val WALLET_PASSWORD = "wallet_pw"
    }

    const val DATUM_TABLE_NAME = "auth_datum"
    object DatumColumns {
        const val ID = "id"
        const val PAYLOAD = "payload"
        const val IV = "iv"
    }

    const val DEFAULT_ID = 1L
}