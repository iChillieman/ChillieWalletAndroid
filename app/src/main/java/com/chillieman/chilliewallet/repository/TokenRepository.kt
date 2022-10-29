package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.BalanceDao
import com.chillieman.chilliewallet.db.dao.TokenDao
import com.chillieman.chilliewallet.db.dao.TokenWatchDao
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.db.entity.TokenWatch
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository
@Inject constructor(
    private val tokenDao: TokenDao,
    private val tokenWatchDao: TokenWatchDao,
    private val balanceDao: BalanceDao
) {
    fun getAllTokensByWalletId(walletId: Long): Single<List<Token>> {
        val tokenList = mutableListOf<Token>()
        return tokenWatchDao.selectAllByWalletId(walletId).map { watchList ->
            watchList.filter {
                it.isWatching
            }.forEach {
                tokenList.add(
                    tokenDao.selectByAddressSynchronously(it.tokenAddress)
                )
            }
            tokenList.toList()
        }
    }

    fun getAllTokensToWatch(): Single<List<TokenWatch>> {
        return tokenWatchDao.selectAll()
    }

    fun insertToken(token: Token, walletId: Long, dexId: Long): Single<Long> {
        //TODO: Ensure the token does not exist, If it does, simply create the TokenWatch record.
        return tokenDao.insert(token)
            .flatMap {
                tokenWatchDao.insert(
                    TokenWatch(
                        walletId,
                        dexId,
                        token.address,
                        true
                    )
                )
            }
    }
//
//
//    fun insertToken(token: Token, walletId: Long, dexId: Long) {
//        tokenDao.insert(token)
//            .map {
//                val watch = TokenWatch(
//
//                )
//            }
//    }

}