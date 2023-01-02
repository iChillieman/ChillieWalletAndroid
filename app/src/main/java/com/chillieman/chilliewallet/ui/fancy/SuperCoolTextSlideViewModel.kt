package com.chillieman.chilliewallet.ui.fancy

import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SuperCoolTextSlideViewModel
@Inject constructor() : BaseViewModel() {
    private val _mainText = MutableLiveData<String>()
    val mainText: MutableLiveData<String>
        get() = _mainText

    private val _secondaryText = MutableLiveData<String>()
    val secondaryText: MutableLiveData<String>
        get() = _secondaryText

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: MutableLiveData<Boolean>
        get() = _isFinished

    fun setMainText(mainText: String) {
        _mainText.value = mainText
    }

    fun setSecondaryText(secondaryText: String) {
        _secondaryText.value = secondaryText
    }

    fun setIsFinished(isFinished: Boolean) {
        _isFinished.value = isFinished
    }

    fun start(mainText: String, secondaryText: String?) {
        _mainText.value = mainText
        secondaryText?.let { _secondaryText.value = it }
        _isFinished.value = false
    }
}