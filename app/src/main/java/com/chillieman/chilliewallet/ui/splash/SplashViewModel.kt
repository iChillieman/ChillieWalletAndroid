package com.chillieman.chilliewallet.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject constructor(
    private val authRepository: AuthRepository,
    private val chillieWalletRepository: ChillieWalletRepository,
) : BaseViewModel() {

    // Hold Loading, Toolbar, Errors
    private val _status = MutableLiveData<SplashStatus>()
    val status: LiveData<SplashStatus>
        get() = _status

    private var statusJob: Job? = null

    fun checkStatus(selectedWalletId: Long) {
        statusJob = viewModelScope.launch {
            _status.value = when {
                !authRepository.isAuthCreated() -> SplashStatus.NEEDS_AUTH
                !chillieWalletRepository.isWalletConfirmed() -> SplashStatus.NEEDS_WALLET
                else -> {
                    SplashStatus.NEEDS_WALLET_SELECTION
                    if (selectedWalletId != -1L) {
                        withContext(Dispatchers.IO) {
                            chillieWalletRepository.updateSelectedWallet(selectedWalletId)
                        }
                        SplashStatus.DEFAULT
                    } else {
                        SplashStatus.NEEDS_WALLET_SELECTION
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        statusJob?.cancel()
        statusJob = null
    }

    companion object {
        private const val TAG = "ChillieSplashVM"
    }
}
