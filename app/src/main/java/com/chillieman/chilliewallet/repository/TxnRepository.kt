package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.TxnDao
import com.chillieman.chilliewallet.db.entity.Txn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxnRepository
@Inject constructor(
    private val txnDao: TxnDao
) {
    suspend fun selectTxnById(id: Long) = txnDao.selectById(id)
    suspend fun selectTxnByTxnString(txn: String) = txnDao.selectByTxn(txn)
    suspend fun selectTxnByWalletId(walletId: Long) = txnDao.selectAllByWalletId(walletId)

    suspend fun insertTxn(txn: Txn) = txnDao.insert(txn)
    suspend fun updateTxn(txn: Txn) = txnDao.update(txn)
    suspend fun getPendingTransactions() = txnDao.selectAllPending()
}