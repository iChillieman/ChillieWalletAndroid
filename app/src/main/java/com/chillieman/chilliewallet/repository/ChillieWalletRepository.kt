package com.chillieman.chilliewallet.repository

import android.util.Log
import com.chillieman.chilliewallet.db.PrefillUtil
import com.chillieman.chilliewallet.db.dao.AuthDao
import com.chillieman.chilliewallet.db.dao.AuthDatumDao
import com.chillieman.chilliewallet.db.dao.ChillieWalletDao
import com.chillieman.chilliewallet.db.dao.DexDao
import com.chillieman.chilliewallet.db.dao.TokenDao
import com.chillieman.chilliewallet.db.dao.TokenWatchDao
import com.chillieman.chilliewallet.db.entity.BlockChain
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.db.entity.TokenWatch
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions
import com.chillieman.chilliewallet.definitions.DexDefinitions
import com.chillieman.chilliewallet.util.EncryptionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.web3j.crypto.Bip44WalletUtils
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ChillieWalletRepository
@Inject constructor(
    private val chillieWalletDao: ChillieWalletDao,
    private val authDao: AuthDao,
    private val authDatumDao: AuthDatumDao,
    private val dexDao: DexDao,
    private val tokenDao: TokenDao,
    private val tokenWatchDao: TokenWatchDao,
    private val encryptionManager: EncryptionManager
) {
    init {
        Log.d("Chillieman", "ChillieWalletRepository INIT!")
    }

    private val _selectedBlockChain = MutableStateFlow<BlockChain?>(null)
    val selectedBlockChain: StateFlow<BlockChain?>
        get() = _selectedBlockChain

    private val _selectedWallet = MutableStateFlow<ChillieWallet?>(null)
    val selectedWallet: StateFlow<ChillieWallet?>
        get() = _selectedWallet

    suspend fun updateSelectedWallet(walletId: Long): ChillieWallet {
        val wallet = chillieWalletDao.selectById(walletId)
        _selectedWallet.value = wallet

        return wallet
    }


    suspend fun importWallet(seed: String, name: String): Boolean {
        if (!MnemonicUtils.validateMnemonic(seed)) {
            Log.d("Chillieman", "Invalid Seed Phrase")
            return false
        }
        try {
            val credentials = Bip44WalletUtils.loadBip44Credentials("", seed)

            Log.d(TAG, "Address: ${credentials.address}")

            val createdWallet =
                createWalletRecord(
                    name,
                    seed,
                    credentials.address,
                    true
                )

            prefillTokensForWalletAlpha(createdWallet.id)
            return true
        } catch (e: Exception) {
            Log.e("Chillieman", "Cant Import Wallet", e)
            return false
        }
    }

    suspend fun createNewWallet(name: String, walletFolderDirectory: File): List<String> {
        val newBip44Wallet = Bip44WalletUtils.generateBip44Wallet("", walletFolderDirectory)
        val address = Bip44WalletUtils.loadBip44Credentials("", newBip44Wallet.mnemonic).address

        Log.d(TAG, "Address: $address")

        try {
            File(walletFolderDirectory, newBip44Wallet.filename).delete()
        } catch (e: Exception) {
            Log.e("Chillieman", "Could not delete Generated Wallet File.")
        }

        val createdWallet =
            createWalletRecord(
                name,
                newBip44Wallet.mnemonic,
                address,
                false
            )

        prefillTokensForWalletAlpha(createdWallet.id)
        return processSeedPhrase(newBip44Wallet.mnemonic)
    }

    suspend fun confirmAlphaWallet() {
        val wallet = fetchAlphaWallet()
        confirmWallet(wallet)
    }

    suspend fun confirmSelectedWallet() {
        val wallet = _selectedWallet.value
            ?: throw IllegalStateException("Now Wallet Selected to Confirm")

        confirmWallet(wallet)
    }


    suspend fun confirmWallet(wallet: ChillieWallet) {
        val confirmedWallet = wallet.copy(isConfirmed = true)
        chillieWalletDao.update(confirmedWallet)
    }


    suspend fun fetchWallet(id: Long): ChillieWallet = chillieWalletDao.selectById(id)
    suspend fun fetchAllWallets(): List<ChillieWallet> = chillieWalletDao.selectAll()

    suspend fun fetchAlphaWallet(): ChillieWallet = chillieWalletDao.selectAll().first()

    suspend fun isWalletCreated() = chillieWalletDao.count() > 0

    suspend fun isWalletConfirmed() = isWalletCreated() && fetchAlphaWallet().isConfirmed


    suspend fun getCredentials(wallet: ChillieWallet): Credentials {
        val startTime = System.currentTimeMillis()
        val credentials = Bip44WalletUtils.loadBip44Credentials("", getWalletSeedPhrase(wallet))
        val elapsedTime = System.currentTimeMillis() - startTime
        Log.d("Chillieman", "Chillieman - Time to fetch Credentials: ${elapsedTime}ms")

        return credentials
    }

    suspend fun clear() {
        chillieWalletDao.clear()
        tokenDao.clear()
        tokenWatchDao.clear()
    }

    private suspend fun createWalletRecord(
        name: String,
        seed: String,
        address: String,
        isConfirmed: Boolean
    ): ChillieWallet {
        // TODO - ALPHA - Remove this restraint
        if (isWalletCreated()) {
            throw IllegalStateException("You already have a wallet, Chill man.")
        }

        val seedId = authDatumDao.insert(encryptionManager.encryptMessage(seed))
        val wallet = ChillieWallet(name, seedId, address, isConfirmed)
        val createdWalletId = chillieWalletDao.insert(wallet)

        return wallet.copy(id = createdWalletId)
    }

    private fun processSeedPhrase(string: String): List<String> {
        val listOfWords = mutableListOf<String>()
        val stringBuilder = StringBuilder()

        string.forEachIndexed { index, c ->
            if (c.isWhitespace()) {
                listOfWords.add(stringBuilder.toString())
                stringBuilder.clear()
            } else if (index == string.length - 1) {
                stringBuilder.append(c)
                listOfWords.add(stringBuilder.toString())
                stringBuilder.clear()
            } else {
                stringBuilder.append(c)
            }
        }

        return listOfWords
    }

    private suspend fun getWalletSeedPhrase(chillieWallet: ChillieWallet): String {
        val wallet = authDatumDao.selectById(chillieWallet.seedId)
        return encryptionManager.decryptMessage(wallet)
    }

    // Token Logic
    suspend fun getAllTokensByWalletId(walletId: Long): List<Token> {
        //This is a list of all the tokens that Users have enabled on thier Wallet.
        val returnList = mutableListOf<Token>()
        tokenWatchDao.selectAllByWalletId(walletId).forEach {
            returnList.add(tokenDao.selectById(it.tokenId))
        }
        return returnList
    }

    private suspend fun prefillTokensForWalletAlpha(walletId: Long) {
        val cakeSwapId = dexDao.selectByChainIdAndRouterAddress(
            BlockChainDefinitions.Binance.CHAIN_ID,
            DexDefinitions.PancakeSwap.ADDRESS_ROUTER
        ).id

        PrefillUtil.loadStartingTokens().forEach {
            insertTokenForUser(it, walletId, cakeSwapId)
        }

        //TODO CHILLIE - Populate TestNet with CHLL (Need to launch on BSC TEST)
    }

    private suspend fun insertTokenForUser(token: Token, walletId: Long, dexId: Long) {
        val tokenId =
            if (tokenDao.countByAddressAndBlockChainId(token.address, token.blockChainId) == 0) {
                // Only add a Token Record if none exist.
                tokenDao.insert(token)
            } else {
                tokenDao.selectByAddressAndBlockChainId(token.address, token.blockChainId).id
            }

        tokenWatchDao.insert(
            TokenWatch(
                walletId = walletId,
                dexId = dexId,
                tokenId = tokenId
            )
        )
    }

    companion object {
        private const val TAG = "ChillieWalletRepo"
    }
}
