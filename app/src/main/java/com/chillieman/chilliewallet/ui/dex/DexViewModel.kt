package com.chillieman.chilliewallet.ui.dex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import javax.inject.Inject

class DexViewModel
@Inject constructor(

): BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Dex Fragment"
    }
    val text: LiveData<String> = _text
}