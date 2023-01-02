package com.chillieman.chilliewallet.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chillieman.chilliewallet.repository.BlockChainRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.repository.DexRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ChillieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val chillieWalletRepository: ChillieWalletRepository,
    private val blockChainRepository: BlockChainRepository,
    private val dexRepository: DexRepository
) : CoroutineWorker(appContext, workerParams) {

    // First, Fetch all wallets.
    //  -- For each Wallet:
    // Fetch all Tokens
    // For each unique BLOCKCHAIN ID, create Web3 Instances. into Mapping
    // For each unique DEX ID, create instances of Router Contract. into Mapping
    // Fetch Token price from each DEX
    // NEED from Router Contract:
    // - Amounts Out
    // - Swap** (Not needed here specifically)
    // - Get Pair
    // NEED from LIQUIDITY PAIR:
    // - getAmounts?
    // NEED from ERC20
    // - balanceOf.
    // -

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val startingTime = System.currentTimeMillis()
            Log.d("Chillie", "Chillieman - Worker is doing work! $startingTime")

            var isSuccess = false
            try {
//                Log.d("Chillie" , "Alpha Wallet: ${alphaWallet.address}")
                isSuccess = true
            } catch (e: Exception) {
                Log.e("ChillieWorker", "Error!", e)
            }
            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startingTime
            Log.d("Chillie", "Chillieman - Worker is done with work! $endTime")
            Log.d("Chillie", "Chillieman - Worker took ${elapsedTime}ms")
            if (isSuccess) {
                Log.d("Chillie", "Chillieman - Worker Success!!")
                Result.success()
            } else {
                Log.d("Chillie", "Chillieman - Worker FAILURE!!")
                Result.failure()
            }
        }
    }
}
