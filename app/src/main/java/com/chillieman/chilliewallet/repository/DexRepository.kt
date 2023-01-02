package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.DexDao
import com.chillieman.chilliewallet.db.entity.Dex
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DexRepository
@Inject constructor(
    private val dexDao: DexDao
) {
    suspend fun getAllDexByBlockChainId(blockChainId: Long): List<Dex> {
        return dexDao.selectAllByChainId(blockChainId)
    }

    suspend fun insertNewDex(dex: Dex): Dex {
        val dbId = dexDao.insert(dex)
        return dex.copy(id = dbId)
    }

    suspend fun insertAllDex(dexs: List<Dex>) = dexDao.insertAll(dexs)

    suspend fun updateDex(dex: Dex) = dexDao.update(dex)

}