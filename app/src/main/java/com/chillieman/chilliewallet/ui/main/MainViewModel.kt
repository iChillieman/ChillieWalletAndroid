package com.chillieman.chilliewallet.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val authRepository: AuthRepository,
    private val chillieWalletRepository: ChillieWalletRepository,
) : BaseViewModel() {

    // Hold Loading, Toolbar, Errors
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // TODO - Do a fun Loading Dialog that says random Loading Messages.

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    val authStatus get() = authRepository.authState.asLiveData()
    var isWelcome = true


    companion object {
        private const val TAG = "ChillieMainVM"
    }
}
