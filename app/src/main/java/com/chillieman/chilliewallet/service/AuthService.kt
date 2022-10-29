package com.chillieman.chilliewallet.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.chillieman.chilliewallet.definitions.UtilDefinitions.ONE_MINUTE
import com.chillieman.chilliewallet.definitions.UtilDefinitions.ONE_SECOND
import com.chillieman.chilliewallet.manager.AuthManager
import com.chillieman.chilliewallet.model.AuthStatus
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class AuthService : BaseService() {
    private val handler = Handler(Looper.getMainLooper())

    @Inject
    lateinit var authManager: AuthManager

    // Start tracking the Time. Reset
    private val unlockDuration = ONE_MINUTE * 10

    private var isLockoutTimerRunning = false

    private val runnable: Runnable = Runnable {
        if (Calendar.getInstance().timeInMillis > authManager.timeWhenUnlocked + unlockDuration) {
            Log.d(TAG, "Timmyyy")
            //User has timed out!
            authManager.setAuthenticationStatus(AuthStatus.UNAUTHENTICATED)
        } else {
            Log.d(TAG, "Timmaa")
            postRunnable()
        }
    }

    private fun postRunnable() {
        handler.postDelayed(runnable, ONE_SECOND * 30L)
    }

    private fun startLockoutTimer() {
        Log.d(TAG, "Starting Lockout Timer")
        isLockoutTimerRunning = true
        postRunnable()
    }

    private fun stopLockoutTimer() {
        handler.removeCallbacks(runnable)
        isLockoutTimerRunning = false
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Created")
        //Set the Status of the AuthManager here

        authManager.authStatus.observeForever {
            when (it) {
                AuthStatus.UNAUTHENTICATED -> {
                    Log.d(TAG, "Unauthenticated")
                    //Stop Monitoring the Auth Status since now you are logged out
                    stopLockoutTimer()
                }
                AuthStatus.AUTHENTICATED -> {
                    Log.d(TAG, "Authenticated")
                    authManager.updatedUnlockedTime()
                    if (!isLockoutTimerRunning) {
                        startLockoutTimer()
                    }
                }
                else -> Unit
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand!")
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Auth Service is Dying!")
        authManager.setAuthenticationStatus(AuthStatus.UNAUTHENTICATED)
        stopLockoutTimer()
    }

    companion object {
        private const val TAG = "ChillieAuthService"
    }
}
