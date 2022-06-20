package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.ChillieWalletDao
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChillieWalletRepository
@Inject constructor(
    private val chillieWalletDao: ChillieWalletDao
){
    fun createWallet(name: String, filePath: String): Single<ChillieWallet> {
        return fetchAllWallets().flatMap {
            if(it.isEmpty()) {
                val id = chillieWalletDao.insert(ChillieWallet(name = name, filePath = filePath))
                loadWallet(id)
            } else {
                throw IllegalStateException("A Wallet already exists. You cant make a new one")
            }
        }
    }

    fun loadWallet(id: Long): Single<ChillieWallet> = chillieWalletDao.selectById(id)

    fun fetchAllWallets() = chillieWalletDao.selectAll()

    fun fetchWallet(): Single<ChillieWallet> {
        return fetchAllWallets().flatMap {
            if(it.isEmpty()) {
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