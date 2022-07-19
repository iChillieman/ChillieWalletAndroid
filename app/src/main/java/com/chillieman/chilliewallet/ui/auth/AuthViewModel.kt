package com.chillieman.chilliewallet.ui.auth


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepository
) : BaseViewModel() {
    private val _isAuthSet = MutableLiveData<Boolean>()
    val isAuthSet: LiveData<Boolean>
        get() = _isAuthSet

    fun isAuthenticationSet() {
        authRepo.isAuthSet()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isAuthSet.value = it
            }, {
                Log.e(TAG, "Error Checking Authentication Status")
            })
            .disposeOnClear()
    }


    companion object {
        private const val TAG = "ChillieAuthVM"
        private const val NODE_URL = "https://bsc-dataseed.binance.org/"
    }
}
