package com.chillieman.chilliewallet.ui.main.wallet


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.definitions.BlockChainDefinitions
import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.model.contracts.IERC20
import com.chillieman.chilliewallet.repository.TokenRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import com.chillieman.chilliewallet.ui.main.MainViewModel
import com.chillieman.chilliewallet.ui.main.wallet.tokenlist.TokenForList
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import javax.inject.Inject

class WalletViewModel
@Inject constructor(
    private val walletManager: WalletManager,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private val _tokensForList = MutableLiveData<List<TokenForList>>()
    val tokensForList: LiveData<List<TokenForList>>
        get() = _tokensForList

    //TODO: Handle all Token logic here. Balances - Worth in ETH
    // - Any pending Orders on the tokens
    // (Tap to be brought to the Order Tab - With Order already selected

    fun loadTokens(chillieWallet: ChillieWallet?) {
        if(chillieWallet == null) {
            Log.w(TAG, "No Wallet Passed")
            return
        }

        // Use a local instance of Web3j
        var tempWeb3j: Web3j? = null
        var creds: Credentials? = null
        Single.fromCallable {
            Web3j.build(HttpService(BlockChainDefinitions.URL_SMART_CHAIN))
        }.flatMap {
            tempWeb3j = it
            walletManager.getCredentials(chillieWallet)
        }.flatMap {
            creds = it
            tokenRepository.getAllTokensByWalletId(chillieWallet.id)
        }.map {
            Log.d(TAG, "LIST SIZE: ${it.size}")
            val tokensForList = mutableListOf<TokenForList>()
            it.forEach { token ->
                val balance = getTokenBalance(chillieWallet, token.address, tempWeb3j, creds).blockingFirst()
                //TODO CHILLIE - Format the Balance (If they have 10 million, display 10.3M)
                val balanceString = balance.shiftLeft(token.decimals).toString()

                val tokenForList = TokenForList(
                    token.name,
                    token.symbol,
                    balanceString,
                    "? BNB", //TODO CHILLIE - Determine the worth of the Token Balance (How much BNB is it worth?)
                    token.address,
                    token.logoUrl
                )

                tokensForList.add(tokenForList)
            }

            tokensForList
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tokensForList.value = it
                tempWeb3j?.shutdown()
                Log.d(TAG, "Tokens loaded successfully!")
            }, {
                tempWeb3j?.shutdown()
                Log.e(TAG, "Error fetching token balances", it)
            })
            .disposeOnClear()
    }

    fun getTokenBalance(wallet: ChillieWallet, tokenAddress: String, web3: Web3j?,  credentials: Credentials?): Flowable<BigInteger> {
        if(web3 == null || credentials == null) {
            Log.e(TAG, "Web3 or Credentials are null!")
            return Flowable.just(BigInteger.valueOf(0))
        }

        val tokenContract = IERC20.load(
            tokenAddress,
            web3,
            credentials,
            DefaultGasProvider()
        )

        return tokenContract.balanceOf(wallet.address).flowable()
    }


    companion object {
        private const val TAG = "ChillieLog"
    }
}
