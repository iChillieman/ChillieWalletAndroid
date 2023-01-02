package com.chillieman.chilliewallet.definitions

object ChillieWalletDefinitions {
    const val TABLE_NAME = "chillie_wallet"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val SEED_ID = "seed_id"
        const val IS_CONFIRMED = "is_confirmed"
        const val ADDRESS = "address"
    }

    const val WALLET_CONTRACT_ADDRESS = "0x93C80a7Ea8fa91f584E5a91cD86E42434E68b210"
    const val WALLET_CONTRACT_TEST_ADDRESS = "" // TODO CHILLIE - FIL ME IN


    const val SELECTED_WALLET_PREFERENCE = "selected_wallet"
}
