package com.chillieman.chilliewallet.ui.wallet


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chillieman.chilliewallet.db.entity.ChillieWallet
import com.chillieman.chilliewallet.db.entity.Token
import com.chillieman.chilliewallet.model.contracts.IERC20
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import com.chillieman.chilliewallet.ui.wallet.select.WalletSelectionItem
import com.chillieman.chilliewallet.ui.wallet.tokenlist.TokenForList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import javax.inject.Inject


@HiltViewModel
class WalletViewModel
@Inject constructor(
    private val walletRepository: ChillieWalletRepository,
    private val web3j: Web3j
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            walletRepository.selectedWallet.collect {
                it?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        loadWalletBalanceAndTokens(it)
                    }
                }
                Log.d("Chillieman", "Chillieman - WalletVM Observed Selected Wallet: ${it?.id}")
            }
        }
    }

    private val _tokensForList = MutableLiveData<List<TokenForList>>()
    val tokensForList: LiveData<List<TokenForList>>
        get() = _tokensForList

    private val _balance = MutableLiveData<BigDecimal>()
    val balance: LiveData<BigDecimal>
        get() = _balance

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _walletSelectionList = MutableLiveData<List<WalletSelectionItem>>()
    val walletSelectionList: LiveData<List<WalletSelectionItem>>
        get() = _walletSelectionList

    var walletJob: Job? = null

    override fun onCleared() {
        super.onCleared()

        walletJob?.cancel()
        walletJob = null
    }

    fun refreshSelectedWallet() {
        val selectedWallet = walletRepository.selectedWallet.value
        Log.d("Chillieman", "Chillieman - Selected Wallet: ${selectedWallet?.id}")

        selectedWallet?.let {
            CoroutineScope(Dispatchers.IO).launch {
                loadWalletBalanceAndTokens(it)
            }
        }
    }


    fun onWalletSelected(walletId: Long) {
        walletJob?.cancel()
        walletJob = CoroutineScope(Dispatchers.IO).launch {
            walletRepository.updateSelectedWallet(walletId)
        }
    }

    fun getAllWallets() {
        walletJob?.cancel()
        walletJob = CoroutineScope(Dispatchers.IO).launch {
            val wallets = walletRepository.fetchAllWallets()
            // Fetch the balance for this wallet
            val listItems = mutableListOf<WalletSelectionItem>()
            wallets.forEach {
                withContext(Dispatchers.Main) {
                    _walletSelectionList.value = listOf(WalletSelectionItem(it, "Loading"))
                }
                val balance = getBalance(it.address)

                val balanceFormatted =
                    Convert.fromWei(balance.toString(), Convert.Unit.ETHER)
                        .round(MathContext(4))
                        .toString()

                listItems.add(WalletSelectionItem(it, balanceFormatted))
            }

            withContext(Dispatchers.Main) {
                _walletSelectionList.value = listItems
            }
        }
    }

    suspend fun getBalance(address: String): BigInteger {
        var balance: BigInteger = BigInteger.ZERO
        web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
            .flowable()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .collect { response ->
                balance = response.balance
            }
        return balance
    }

    suspend fun getTokenBalance(tokenContract: IERC20, address: String): BigInteger {
        var balance: BigInteger = BigInteger.ZERO
        tokenContract.balanceOf(address).flowable()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .collect {
                balance = it
            }
        return balance
    }


    private suspend fun loadWalletBalanceAndTokens(wallet: ChillieWallet) {
        val balance = web3j.ethGetBalance(wallet.address, DefaultBlockParameterName.LATEST).send()


        val balanceFormatted =
//            if(balance.balance == BigInteger.ZERO) {
//            BigDecimal.ZERO
//        } else {
            Convert.fromWei(balance.balance.toString(), Convert.Unit.ETHER)
                .round(MathContext(4))
//        }


        withContext(Dispatchers.Main) {
            _address.value = wallet.address
            _balance.value = balanceFormatted
        }

        loadTokens(wallet)
    }


    private suspend fun getTokenBalances(
        creds: Credentials,
        tokens: List<Token>
    ): List<TokenForList> {
        return flow {
            tokens.forEach { token ->
                Log.w("Chillie", "Loading: ${token.address}")

                val tokenContract = IERC20.load(
                    token.address,
                    web3j,
                    creds,
                    DefaultGasProvider()
                )

                val tokenBalance = getTokenBalance(tokenContract, token.address)
                Log.d("Chillieman", "Chillieman - Token Balance: $tokenBalance")
                val balanceString = tokenBalance.shiftLeft(token.decimals).toString()
                val tokenForList = TokenForList(
                    token.name,
                    token.symbol,
                    balanceString,
                    "? BNB", //TODO CHILLIE - Determine the worth of the Token Balance (How much BNB is it worth?)
                    token.address,
                    token.logoUrl
                )
                emit(tokenForList)
            }
        }
            .map { item ->
                Log.w("Chillie", "Finished Loading: ${item.address}")
                item
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }

    private suspend fun loadTokens(chillieWallet: ChillieWallet) {
        // Use a local instance of Web3j
        val creds = walletRepository.getCredentials(chillieWallet)
        val tokens = walletRepository.getAllTokensByWalletId(chillieWallet.id)

        val tokensForList = mutableListOf<TokenForList>()
        tokens.forEach { token ->
            Log.w("Chillie", "Loading: ${token.address}")
            val tokenContract = IERC20.load(
                token.address,
                web3j,
                creds,
                DefaultGasProvider()
            )


            val tokenBalance = getTokenBalance(tokenContract, chillieWallet.address)
            Log.d("Chillieman", "Chillieman - Token Balance: $tokenBalance")

            //TODO CHILLIE - Format the Balance (If they have 10 million, display 10.3M
            // )
            val balanceString = tokenBalance.shiftLeft(token.decimals).toString()

            val tokenForList = TokenForList(
                token.name,
                token.symbol,
                balanceString,
                "? BNB", //TODO CHILLIE - Determine the worth of the Token Balance (How much BNB is it worth?)
                token.address,
                token.logoUrl
            )

            tokensForList.add(tokenForList)
            Log.w("Chillie", "Finished Loading: ${token.address}")
        }

        val blahhh = getTokenBalances(creds, tokens)
        withContext(Dispatchers.Main) {
            _tokensForList.value = blahhh
        }
    }

    companion object {
        private const val TAG = "ChillieWalletVM"
    }
}
