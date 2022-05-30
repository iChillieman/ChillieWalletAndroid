package com.chillieman.chilliewallet.definitions

object AuthenticationDefinitions {
    const val TABLE_NAME = "auth"
    object Columns {
        const val ID = "id"
        const val PIN = "pin"
        const val PASSPHRASE = "passphrase"
    }

    const val DEFAULT_ID = 1L
}