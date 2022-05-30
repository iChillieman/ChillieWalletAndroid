package com.chillieman.chilliewallet.ui.main.bot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import javax.inject.Inject

class BotViewModel
@Inject constructor(

) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Bot Fragment"
    }
    val text: LiveData<String> = _text
}