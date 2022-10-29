package com.chillieman.chilliewallet.definitions

object ChilliePairDefinitions {
    const val TABLE_NAME = "pairs"

    object Columns {
        const val ID = "id"
        const val BLOCKCHAIN_ID = "blockchain_id"
        const val ADDRESS = "address"
        const val TOKEN_0 = "token0"
        const val TOKEN_1 = "token1"
        const val RESERVE_0 = "reserve0"
        const val RESERVE_1 = "reserve1"
    }
}