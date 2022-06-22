package com.chillieman.chilliewallet.ui.main.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.manager.WalletManager
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WalletViewModel
@Inject constructor(
    private val walletManager: WalletManager
) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Launch the BarcodeActivity"
    }
    val text: LiveData<String> = _text



    // Auth Stuff:
    private val _isCreating = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isCreating: LiveData<Boolean> = _isCreating



    companion object {
        private const val TAG = "ChillieLog"
    }
}