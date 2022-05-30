package com.chillieman.chilliewallet.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel
@Inject constructor() : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Launch QR Playground"
    }
    val text: LiveData<String>
        get() = _text

    fun changeText(text: String) {
        _text.value = text
    }
}