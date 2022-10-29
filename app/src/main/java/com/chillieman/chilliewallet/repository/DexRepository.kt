package com.chillieman.chilliewallet.repository

import com.chillieman.chilliewallet.db.dao.DexDao
import com.chillieman.chilliewallet.db.entity.Dex
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions
import com.chillieman.chilliewallet.definitions.DexDefinitions
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DexRepository
@Inject constructor(
    private val dexDao: DexDao
){
    fun getAllDexByBlockChainId(blockChainId: Long): Single<List<Dex>> {
        return dexDao.selectAllByChainId(blockChainId)
    }

    fun insertNewDex(dex: Dex): Single<Dex> {
        return dexDao.insert(dex).map {
            dex.copy(id = it)
        }
    }

    fun insertAllDex(dexs: List<Dex>): Completable {
        return dexDao.insertAll(dexs)
    }

    fun updateDex(dex: Dex): Single<Int> {
        return dexDao.update(dex)
    }

    fun getPancakeSwapDexId(): Single<Long> {
        return dexDao.selectByChainIdAndRouterAddress(
            BlockChainDefinitions.CHAIN_ID_SMART_CHAIN,
            DexDefinitions.DEX_PANCAKE_SWAP_ADDRESS_ROUTER
        ).map {
            it.id
        }
    }
}