package com.chillieman.chilliewallet.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.definitions.UtilDefinitions.ONE_MINUTE
import com.chillieman.chilliewallet.definitions.UtilDefinitions.ONE_SECOND
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.repository.AuthRepository
import com.chillieman.chilliewallet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class AuthService : BaseService() {
    @Inject
    lateinit var authRepository: AuthRepository

    private var watchJob: Job? = null
    private var lockoutJob: Job? = null

    // Start tracking the Time. Reset
    private val unlockDuration = ONE_MINUTE * 10

    private var isLockoutTimerRunning = false

    private fun startLockoutTimer() {
        Log.d(TAG, "Chillieman - Starting Lockout Timer")
        isLockoutTimerRunning = true
        lockoutJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                Log.d(TAG, "Chillieman - Timmyyy ${System.currentTimeMillis()}")
                if (System.currentTimeMillis() > authRepository.timeWhenUnlocked + unlockDuration) {
                    //User has timed out!
                    authRepository.setAuthenticationStatus(AuthStatus.UNAUTHENTICATED)
                }

                // Wait for 30 seconds before checking again
                delay(ONE_SECOND * 30L)
            }
        }
    }

    private fun stopLockoutTimer() {
        lockoutJob?.cancel()
        isLockoutTimerRunning = false
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Chillieman - Auth Service Created")
        //Set the Status of the AuthManager here
        startLockoutTimer()

        Log.d(TAG, "Chillieman - Auth State: ${authRepository.authState.value}")

        watchJob = CoroutineScope(Dispatchers.Default).launch {
            authRepository.authState.collect {
                when (it) {
                    AuthStatus.UNAUTHENTICATED -> {
                        Log.d(TAG, "Chillieman - Unauthenticated")
                        // TODO - CHILLIEMAN - PUT THIS BACK
                        //Stop Monitoring the Auth Status since now you are logged out
//                        stopLockoutTimer()
                    }
                    AuthStatus.AUTHENTICATED -> {
                        Log.d(TAG, "Chillieman - Authenticated")
                        authRepository.updatedUnlockedTime()
                        // TODO - CHILLIEMAN - PUT THIS BACK
//                        if (!isLockoutTimerRunning) {
//                            startLockoutTimer()
//                        }
                    }
                    else -> Unit
                }
            }
        }


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Chillieman - Auth Service onStartCommand! - SHTICKY!!")
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = createNotificationChannel("Chillieman", "My Chillieman Service")

        val notification = NotificationCompat.Builder(this, channelId)
//            .setContentTitle("Chillie Service is working")
            .setContentText("Fulfilling Order...")
            .setSmallIcon(R.drawable.ic_chillie_notification)
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(101, notification)

//        val notification = Notification.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID)
//            .setContentTitle("Chillie Service")
//            .setContentText("Dont cancel me!")
//            .setSmallIcon(R.drawable.logo_bnb)
//            .setContentIntent(pendingIntent)
//            .build();
//
//        // Start the service in the foreground and display the notification
//        startForeground(1, notification);


        // Perform the service's work here

        // Return START_STICKY to tell the system to recreate the service as soon as possible
        // after it has been destroyed
        return START_STICKY;
    }


    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Chillieman - Auth Service is Dying!")
        authRepository.setAuthenticationStatus(AuthStatus.UNAUTHENTICATED)
        watchJob?.cancel()
        watchJob = null
        lockoutJob?.cancel()
        lockoutJob = null
        stopLockoutTimer()
    }

    companion object {
        private const val TAG = "ChillieAuthService"
    }
}
