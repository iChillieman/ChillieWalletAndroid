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

    private val cachedCredentials = HashMap<ChillieWallet, Credentials>()
    private var cachedWalletPassword: String? = null

    fun isWalletCreated() = chillieWalletRepository.isWalletCreated()

    private fun getCredentialsFromWallet(chillieWallet: ChillieWallet): Single<Credentials> {
        return Single.fromCallable {
            if (cachedWalletPassword != null) {
                WalletUtils.loadCredentials(cachedWalletPassword, chillieWallet.filePath)
            } else {
                WalletUtils.loadCredentials(
                    authRepository.getWalletPassword().blockingGet(),
                    chillieWallet.filePath
                )
            }
        }.map {
            cachedCredentials[chillieWallet] = it
            it
        }
    }


    fun getSelectedWalletCredentials(): Single<Credentials> {
        return with(_selectedWallet.value) {
            if (this == null) {
                createWalletOrSelectExistingSingle().flatMap {
                    _selectedWallet.postValue(it)
                    getCredentialsFromWallet(it)
                }
            } else {
                if (cachedCredentials.containsKey(this)) {
                    Log.d(TAG, "Cache me outside")
                    Single.just(cachedCredentials[this])
                } else {
                    getCredentialsFromWallet(this)
                }
            }
        }
    }



    fun createNewWallet(): Single<Bip39Wallet> {
        return authRepository.getWalletPassword().map {
            WalletUtils.generateBip39Wallet(it, walletFolderPath)
        }
    }

    fun enterIntoDatabase(wallet: Bip39Wallet): Completable {
        return chillieWalletRepository.isWalletCreated().flatMapCompletable { isWalletExisting ->
            if (isWalletExisting) {
                //It Exists!
                Log.d(TAG, "createWallet: Wallet is already created!")
                throw IllegalStateException("Wallet is already created, and your trying to make another")
                //TODO: Support Multiple Wallets
            } else {
                Log.d(TAG, "Wallet generated: ${wallet.filename}")
                Log.d(TAG, "Phrase: ${wallet.mnemonic}")

                val walletFile = File(walletFolderPath, wallet.filename)
                Log.d(TAG, "Full Wallet Path: ${walletFile.absolutePath}")

                val createdWallet = chillieWalletRepository.createWallet(
                    "Chillieman",
                    walletFile.absolutePath
                ).blockingGet()

                _selectedWallet.postValue(createdWallet)
                Completable.complete()
            }
        }
    }

    // This logic is for Alpha - Users can only control one Wallet
    private fun createWalletOrSelectExisting(): Completable {
        //First see if the wallet exists:
        return createWalletOrSelectExistingSingle().flatMapCompletable {
            _selectedWallet.postValue(it)
            Completable.complete()
        }
    }

    // This logic is for Alpha - Users can only control one Wallet
    private fun createWalletOrSelectExistingSingle(): Single<ChillieWallet> {
        //First see if the wallet exists:
        return chillieWalletRepository.isWalletCreated().flatMap { isWalletExisting ->
            if (isWalletExisting) {
                //It Exists!
                Log.d(TAG, "createWallet: Wallet is already created!")
                chillieWalletRepository.fetchWallet()
            } else {
                val newWallet = createNewWallet().blockingGet()
                Log.d(TAG, "Wallet generated: ${newWallet.filename}")
                Log.d(TAG, "Phrase: ${newWallet.mnemonic}")

                val walletFile = File(walletFolderPath, newWallet.filename)
                Log.d(TAG, "Full Wallet Path: ${walletFile.absolutePath}")

                chillieWalletRepository.createWallet("Chillieman", walletFile.absolutePath)
            }
        }
    }


    companion object {
        private const val TAG = "ChillieLog - WalletManager"
    }
}