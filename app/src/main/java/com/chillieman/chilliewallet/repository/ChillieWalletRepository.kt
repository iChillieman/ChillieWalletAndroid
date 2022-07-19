package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.AuthDatumDao
import com.chillieman.chilliewallet.db.dao.ChillieWalletDao
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.manager.EncryptionManager
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChillieWalletRepository
@Inject constructor(
    private val chillieWalletDao: ChillieWalletDao,
    private val authDatumDao: AuthDatumDao,
    private val encryptionManager: EncryptionManager
) {
    fun createWallet(name: String, filePath: String, seed: String): Single<ChillieWallet> {
        return fetchAllWallets().flatMap {
            if (it.isEmpty()) {
                encryptionManager.encryptMessage(seed)
            } else {
                throw IllegalStateException("A Wallet already exists. You cant make a new one")
            }
        }.flatMap {
            authDatumDao.insert(it)
        }.flatMap {
            val id = chillieWalletDao.insert(
                ChillieWallet(
                    name = name,
                    filePath = filePath,
                    seedId = it
                )
            )
            loadWallet(id)
        }
    }

    fun getWalletSeedPhrase(chillieWallet: ChillieWallet): Single<String> =
        authDatumDao.selectById(chillieWallet.seedId).flatMap {
            encryptionManager.decryptMessage(it)
        }

    fun loadWallet(id: Long): Single<ChillieWallet> = chillieWalletDao.selectById(id)

    fun fetchAllWallets() = chillieWalletDao.selectAll()

    fun confirmWallet(wallet: ChillieWallet): Single<Int> {
            return chillieWalletDao.update(
                ChillieWallet(
                    wallet.name,
                    wallet.filePath,
                    wallet.seedId,
                    true,
                    wallet.id
                )
            )
    }

    fun fetchWallet(): Single<ChillieWallet> {
        return fetchAllWallets().flatMap {
            if (it.isEmpty()) {
                throw IllegalStateException("A Wallet doent exist =[")
            } else {
                Single.just(it.first())
            }
        }
    }

    fun isWalletCreated() = chillieWalletDao.count().map {
        it > 0
    }

}
