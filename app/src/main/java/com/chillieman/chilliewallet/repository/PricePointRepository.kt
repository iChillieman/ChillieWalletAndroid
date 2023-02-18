package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.PricePointDao
import com.chillieman.chilliewallet.db.entity.PricePoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PricePointRepository
@Inject constructor(
    private val pricePointDao: PricePointDao
) {
    //Returns ID of inserted Price Point.
    suspend fun insertPricePoint(pricePoint: PricePoint) = pricePointDao.insert(pricePoint)
}