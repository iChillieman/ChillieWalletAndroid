package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.ChillieOrderDao
import com.chillieman.chilliewallet.db.dao.ChillieOrderStepDao
import com.chillieman.chilliewallet.db.dao.TxnDao
import com.chillieman.chilliewallet.db.entity.ChillieOrder
import com.chillieman.chilliewallet.db.entity.ChillieOrderStep
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChillieOrderRepository
@Inject constructor(
    private val txnDao: TxnDao,
    private val orderDao: ChillieOrderDao,
    private val orderStepDao: ChillieOrderStepDao
) {
    suspend fun createChillieOrder(order: ChillieOrder, steps: List<ChillieOrderStep>): Boolean {
        return try {
            val orderId = orderDao.insert(order)
            val processedSteps = mutableListOf<ChillieOrderStep>()
            steps.forEach {
                processedSteps.add(it.copy(chillieOrderId = orderId))
            }
            orderStepDao.insertAll(processedSteps)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun fetchStepsByOrderId(orderId: Long) = orderStepDao.selectAllByChillieOrderId(orderId)

    suspend fun updateStep(orderStep: ChillieOrderStep) = orderStepDao.update(orderStep)

    suspend fun updateOrder(order: ChillieOrder) = orderDao.update(order)

    suspend fun fetchStepByTxnString(txn: String): ChillieOrderStep {
        val txnObject = txnDao.selectByTxn(txn)
        return orderStepDao.selectByTxnId(txnObject.id)
    }
}