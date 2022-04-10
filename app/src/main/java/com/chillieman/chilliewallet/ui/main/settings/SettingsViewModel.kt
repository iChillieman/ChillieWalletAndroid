package com.chillieman.chilliewallet.ui.main.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(

) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Settings Fragment"
    }
    val text: LiveData<String> = _text
}