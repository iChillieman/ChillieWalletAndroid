package com.chillieman.chilliewallet.manager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.web3j.crypto.Bip39Wallet
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletManager
@Inject constructor(
    private val authRepository: AuthRepository,
    private val chillieWalletRepository: ChillieWalletRepository,
    private val walletFolderPath: File
) {
    private val _selectedWallet = MutableLiveData<ChillieWallet>()
    val selectedWallet: LiveData<ChillieWallet>
        get() = _selectedWallet

    private val cachedCredentials = HashMap<Long, Credentials>()

    fun isWalletCreated() = chillieWalletRepository.isWalletCreated()

    fun isWalletConfirmed(): Single<Boolean> = chillieWalletRepository.fetchWallet().map {
        it.isConfirmed
    }

    fun onConfirmWallet(): Single<Int> = chillieWalletRepository.fetchWallet().flatMap {
        chillieWalletRepository.confirmWallet(it)
    }

    private fun getCredentialsFromWallet(chillieWallet: ChillieWallet): Single<Credentials> {
        return Single.fromCallable {
            WalletUtils.loadCredentials(
                authRepository.getWalletPassword().blockingGet(),
                chillieWallet.filePath
            )
        }.map {
            cachedCredentials[chillieWallet.id] = it
            it
        }
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

    fun getAlphaWalletSeedPhrase(): Single<List<String>> {
        return chillieWalletRepository.fetchWallet().flatMap {
            chillieWalletRepository.getWalletSeedPhrase(it)
        }.map {
            processSeedPhrase(it)
        }
    }

    private fun getSelectedWalletSeedPhrase(): Single<List<String>> {
        return _selectedWallet.value?.let {
            chillieWalletRepository.getWalletSeedPhrase(it)
        }?.map {
            processSeedPhrase(it)
        } ?: throw IllegalStateException("Wallet Is Not Selected...")
    }

    fun getSelectedWalletCredentials(): Single<Credentials> {
        return _selectedWallet.value?.let {
            if (cachedCredentials.containsKey(it.id)) {
                Log.d(TAG, "Cache me outside")
                Single.just(cachedCredentials[it.id])
            } else {
                getCredentialsFromWallet(it)
            }
        } ?: throw IllegalStateException("No Wallet Selected!")
    }

    fun createNewWallet(): Single<List<String>> {
        return authRepository.getWalletPassword().map {
            WalletUtils.generateBip39Wallet(it, walletFolderPath)
        }.map { wallet ->
            Log.d(TAG, "Wallet generated: ${wallet.filename}")
            Log.d(TAG, "Phrase: ${wallet.mnemonic}")

            val walletFile = File(walletFolderPath, wallet.filename)
            Log.d(TAG, "Full Wallet Path: ${walletFile.absolutePath}")

            val createdWallet = chillieWalletRepository.createWallet(
                "Chillieman",
                walletFile.absolutePath,
                wallet.mnemonic
            ).blockingGet()

            _selectedWallet.postValue(createdWallet)
            processSeedPhrase(wallet.mnemonic)
        }
    }

    fun loadAlphaWallet(): Single<ChillieWallet> {
        return chillieWalletRepository.fetchWallet().map {
            _selectedWallet.postValue(it)
            it
        }
    }

    companion object {
        private const val TAG = "ChillieLog - WalletManager"
    }
}
