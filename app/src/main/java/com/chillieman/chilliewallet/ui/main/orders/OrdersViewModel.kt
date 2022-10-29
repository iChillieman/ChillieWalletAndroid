package com.chillieman.chilliewallet.ui.main.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel
@Inject constructor(

) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value =
            "This is the Orders Fragment - List out all orders - Make it simple to create a new one!"
    }
    val text: LiveData<String> = _text
}
