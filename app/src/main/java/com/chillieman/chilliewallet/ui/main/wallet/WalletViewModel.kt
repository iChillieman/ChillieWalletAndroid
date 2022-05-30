package com.chillieman.chilliewallet.ui.main.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import javax.inject.Inject

class WalletViewModel
@Inject constructor(

) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Launch the BarcodeActivity"
    }
    val text: LiveData<String> = _text
}