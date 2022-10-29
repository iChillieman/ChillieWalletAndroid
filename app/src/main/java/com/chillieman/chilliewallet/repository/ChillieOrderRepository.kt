package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.ChillieOrderDao
import com.chillieman.chilliewallet.db.dao.ChillieOrderStepDao
import com.chillieman.chilliewallet.db.dao.TxnDao
import com.chillieman.chilliewallet.db.entity.ChillieOrder
import com.chillieman.chilliewallet.db.entity.ChillieOrderStep
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChillieOrderRepository
@Inject constructor(
    private val txnDao: TxnDao,
    private val orderDao: ChillieOrderDao,
    private val orderStepDao: ChillieOrderStepDao
) {
    fun createChillieOrder(order: ChillieOrder, steps: List<ChillieOrderStep>): Completable {
        return orderDao.insert(order).flatMapCompletable { orderId ->
            val newSteps = mutableListOf<ChillieOrderStep>()
            steps.forEach {
                newSteps.add(
                    it.copy(chillieOrderId = orderId)
                )
            }

            orderStepDao.insertAll(newSteps)
        }
    }

    fun fetchStepsByOrderId(orderId: Long) = orderStepDao.selectAllByChillieOrderId(orderId)

    fun updateStep(orderStep: ChillieOrderStep) = orderStepDao.update(orderStep)

    fun updateOrder(order: ChillieOrder) = orderDao.update(order)

    fun fetchStepByTxnString(txn: String) = txnDao.selectByTxn(txn).flatMap {
        orderStepDao.selectByTxnId(it.id)
    }


}