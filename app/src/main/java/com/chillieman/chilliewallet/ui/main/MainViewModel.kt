package com.chillieman.chilliewallet.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.manager.AuthManager
import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Convert
import java.math.BigDecimal
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val authManager: AuthManager,
    private val walletManager: WalletManager,
    private val web3j: Web3j
) : BaseViewModel() {
    private val _isWalletCreated = MutableLiveData<Boolean>()
    val isWalletCreated: LiveData<Boolean>
        get() = _isWalletCreated

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val selectedWallet get() = walletManager.selectedWallet
    val authStatus get() = authManager.authStatus

    private val _balance = MutableLiveData<BigDecimal>()
    val balance: LiveData<BigDecimal>
        get() = _balance

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    fun checkAuth() {
        authManager.isAuthCreated()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it) {
                    //Its already Created
                    authManager.setAuthenticationStatus(AuthStatus.UNAUTHENTICATED)
                } else {
                    //It needs to be created!
                    authManager.setAuthenticationStatus(AuthStatus.NEED_TO_CREATE)
                }

            }, {
                Log.e(TAG, "Error checking if Auth Exists", it)
            }).disposeOnClear()
    }

    var isWalletCheckStarted = false

    @SuppressLint("CheckResult")
    fun checkWallet(): Boolean {
        if (isWalletCheckStarted) {
            return true
        }
        isWalletCheckStarted = true
        _isLoading.value = true

        walletManager.isWalletCreated().flatMapCompletable {
            if (!it) {
                _isWalletCreated.postValue(false)
            } else {
                //Automatically load the Wallet
                walletManager.getSelectedWalletCredentials().blockingGet()
            }
            Completable.complete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Wallet Loaded Successfully, or Doesnt Exist")
                _isLoading.value = false
            }, {
                Log.e(TAG, "Error checking wallet exists", it)
            }).disposeOnClear()
        return false
    }





    fun getAddress() {
        walletManager.getSelectedWalletCredentials()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _address.value = it.address
            }, {
                Log.e(TAG, "Could NOT fetch Balance", it)
            }).disposeOnClear()
    }

    fun getBalance() {
        walletManager.getSelectedWalletCredentials().map {
            web3j.ethGetBalance(it.address, DefaultBlockParameterName.LATEST).send()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _balance.value = Convert.fromWei(
                    it.balance.toString(),
                    Convert.Unit.ETHER
                )
            }, {
                Log.e(TAG, "Could NOT fetch Balance", it)
            }).disposeOnClear()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}