package com.chillieman.chilliewallet.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.repository.ChillieWalletRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel
@Inject constructor(
    private val walletRepository: ChillieWalletRepository,
    private val authRepository: AuthRepository,
    private val walletFolder: File
) : BaseViewModel() {
    // Live Data
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorStringId = MutableLiveData<Int>()
    val errorStringId: LiveData<Int>
        get() = _errorStringId

    private val _passwordErrorIdList = MutableLiveData<List<Int>>()
    val passwordErrorIdList: LiveData<List<Int>>
        get() = _passwordErrorIdList

    private val _isPasswordReady = MutableLiveData<Boolean>()
    val isPasswordReady: LiveData<Boolean>
        get() = _isPasswordReady

    private val _isAuthCreated = MutableLiveData<Boolean>()
    val isAuthCreated: LiveData<Boolean>
        get() = _isAuthCreated

    private val _isWalletConfirmed = MutableLiveData<Boolean>()
    val isWalletConfirmed: LiveData<Boolean>
        get() = _isWalletConfirmed

    private val _wordList = MutableLiveData<List<String>>()
    val wordList: LiveData<List<String>>
        get() = _wordList

    private val _pinTitle = MutableLiveData<Int>().apply { value = R.string.pin_new }
    val pinTitle: LiveData<Int>
        get() = _pinTitle

    // Jobs
    private var passwordErrorCheckerJob: Job? = null
    private var walletJob: Job? = null
    private var seedJob: Job? = null
    private var authJob: Job? = null

    // Other
    private var tempPin: String? = null
    private var tempPass: String? = null


    override fun onCleared() {
        super.onCleared()

        passwordErrorCheckerJob?.cancel()
        walletJob?.cancel()
        seedJob?.cancel()
        authJob?.cancel()

        passwordErrorCheckerJob = null
        walletJob = null
        seedJob = null
        authJob = null
    }

    fun consumeError() {
        _errorStringId.value = 0
    }

    fun importWallet(input: String) {
        walletJob?.cancel()
        walletJob = CoroutineScope(Dispatchers.IO).launch {
            // TODO - ALPHA - REMOVE THIS CONSTRAINT
            clearExistingWalletFilesIfNeeded()

            val isSuccess = walletRepository.importWallet(input, "ChillieWallet")

            withContext(Dispatchers.Main) {
                if (isSuccess) {
                    _isWalletConfirmed.value = true
                } else {
                    _errorStringId.value = R.string.error_seed_incorrect
                }
            }
        }
    }


    fun createWallet() {
        walletJob?.cancel()
        walletJob = CoroutineScope(Dispatchers.IO).launch {
            // TODO - ALPHA - REMOVE THIS CONSTRAINT
            clearExistingWalletFilesIfNeeded()

            val tryThis = walletRepository.createNewWallet("ChillieWallet", walletFolder)
            withContext(Dispatchers.Main) {
                _wordList.value = tryThis
            }
        }
    }


    fun isSeedConfirmationValid(input: String) {
        seedJob?.cancel()
        seedJob = viewModelScope.launch {
            val wordList = mutableListOf<String>()
            input.trim().split(' ').forEach {
                wordList.add(it.trim())
            }

            if (wordList.size != 12) {
                _errorStringId.value = R.string.error_seed_word_size
            }
            val existingWordList =
                _wordList.value ?: throw IllegalStateException("No ExistingWordList")
            if (wordList.containsAll(existingWordList)) {
                withContext(Dispatchers.IO) { walletRepository.confirmAlphaWallet() }
                _isWalletConfirmed.value = true
            }
        }
    }

    fun updatePasswordErrorList(input: String, input2: String) {
        passwordErrorCheckerJob?.cancel()
        passwordErrorCheckerJob = viewModelScope.launch {
            val password = input.trim()
            val errorList = mutableListOf<Int>()
            val hasUpper = password.any { it.isUpperCase() }
            if (!hasUpper) {
                errorList.add(R.string.error_password_no_upper)
            }

            val hasLower = password.any { it.isLowerCase() }
            if (!hasLower) {
                errorList.add(R.string.error_password_no_lower)
            }

            val hasDigit = password.any { it.isDigit() }
            if (!hasDigit) {
                errorList.add(R.string.error_password_no_digit)
            }

//            val hasSpecial = password.matches(Regex("[$&+,:;=\\\\?@#|/'<>.^*()%!-]"))
//            if (!hasSpecial) {
//                errorList.add(R.string.error_password_no_special)
//            }

            val isPasswordLongEnough = password.length > 10
            if (!isPasswordLongEnough) {
                errorList.add(R.string.error_password_too_short)
            }


            if (input2.isNotEmpty() && input != input2) {
                errorList.add(R.string.error_password_do_not_match)
            }

            val existingErrors = _passwordErrorIdList.value ?: listOf()
            if (errorList.size != existingErrors.size || !errorList.containsAll(existingErrors)) {
                _passwordErrorIdList.value = errorList
            }
        }
    }

    fun processPassword(input1: String, input2: String) {
        val pass = input1.trim()
        if (pass == input2.trim() && isPasswordValid(pass)) {
            tempPass = pass
            _isPasswordReady.value = true
        }
    }

    fun processesPin(pin: String) {
        val currentTitle = _pinTitle.value ?: -1
        if (tempPin == null) {
            tempPin = pin
            if (currentTitle != R.string.pin_new_confirm) {
                _pinTitle.value = R.string.pin_new_confirm
            }
        } else {
            if (pin != tempPin) {
                if (currentTitle != R.string.pin_new) {
                    _pinTitle.value = R.string.pin_new
                }
                // User messed up, try again.
                tempPin = null
                _errorStringId.value = R.string.error_pin_do_not_match
            } else {
                createAuthIfReady()
            }
        }
    }

    private fun createAuthIfReady() {
        authJob?.cancel()
        authJob = CoroutineScope(Dispatchers.IO).launch {
            val password = tempPass
            val pin = tempPin
            if (!pin.isNullOrEmpty() && !password.isNullOrEmpty()) {
                authRepository.createAuthData(password = password, pin = pin)
                withContext(Dispatchers.Main) { _isAuthCreated.value = true }
            }
        }
    }

    private fun isPasswordValid(input: String): Boolean {
        var isValid = true
        val password = input.trim()
        val errorList = mutableListOf<Int>()
        val hasUpper = password.any { it.isUpperCase() }
        if (!hasUpper) {
            isValid = false
            errorList.add(R.string.error_password_no_upper)
        }

        val hasLower = password.any { it.isLowerCase() }
        if (!hasLower) {
            isValid = false
            errorList.add(R.string.error_password_no_lower)
        }

        val hasDigit = password.any { it.isDigit() }
        if (!hasDigit) {
            isValid = false
            errorList.add(R.string.error_password_no_lower)
        }

        // TODO - FIX THIS
//        val hasSpecial = password.matches(Regex("[$&+,:;=\\\\?@#|/'<>.^*()%!-]"))
//        if (!hasSpecial) {
//            isValid = false
//            errorList.add(R.string.error_password_no_special)
//        }

        val isPasswordLongEnough = password.length > 10
        if (!isPasswordLongEnough) {
            isValid = false
            errorList.add(R.string.error_password_too_short)
        }


        val existingErrors = _passwordErrorIdList.value ?: listOf()
        if (errorList.size != existingErrors.size || !errorList.containsAll(existingErrors)) {
            _passwordErrorIdList.value = errorList
        }


        return isValid
    }

    private suspend fun clearExistingWalletFilesIfNeeded() {
        val isWalletCreated = walletRepository.isWalletCreated()
        if (isWalletCreated) {
            // DELETE THE CONTENTS OF WALLET FOLDER
            walletFolder.listFiles()?.forEach {
                try {
                    it.delete()
                } catch (e: Exception) {
                    return@forEach
                }
            }
            walletRepository.clear()
        }
    }

}