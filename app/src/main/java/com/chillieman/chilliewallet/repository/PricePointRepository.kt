package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.PricePointDao
import com.chillieman.chilliewallet.db.entity.PricePoint
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PricePointRepository
@Inject constructor(
    private val pricePointDao: PricePointDao
) {

    //Returns ID of inserted Price Point.
    fun insertPricePoint(pricePoint: PricePoint): Single<Long> {
        return pricePointDao.insert(pricePoint)
    }


}