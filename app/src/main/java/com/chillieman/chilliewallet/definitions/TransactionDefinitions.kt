package com.chillieman.chilliewallet.definitions

object TransactionDefinitions {
    const val TABLE_NAME = "txn"

    object Columns {
        const val ID = "id"
        const val WALLET_ID = "wallet_id"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val TOKEN_ADDRESS = "token_address"
        const val TXN = "txn_number"
        const val CONTENT = "content_json"
        const val IS_SUCCESS = "is_success"
        const val TIMESTAMP = "timestamp"
    }
}
