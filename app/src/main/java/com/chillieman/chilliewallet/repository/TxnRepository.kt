package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.TxnDao
import com.chillieman.chilliewallet.db.entity.Txn
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxnRepository
@Inject constructor(
    private val txnDao: TxnDao
) {
    fun selectTxnById(id: Long) = txnDao.selectById(id)
    fun selectTxnByTxnString(txn: String) = txnDao.selectByTxn(txn)
    fun selectTxnByWalletId(walletId: Long) = txnDao.selectAllByWalletId(walletId)
    fun selectTxnByWalletAndTokenId(walletId: Long, tokenAddress: String) =
        txnDao.selectAllByWalletAndTokenAddress(walletId, tokenAddress)



    fun insertTxn(txn: Txn) = txnDao.insert(txn)
    fun updateTxn(txn: Txn) = txnDao.update(txn)

    fun getPendingTransactions(): Single<List<Txn>> {
        return txnDao.selectAllPending()
    }


}