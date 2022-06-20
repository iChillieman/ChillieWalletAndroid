package com.chillieman.chilliewallet.service

import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.chillieman.chilliewallet.definitions.IntentDefinitions.EXTRA_NEED_TO_CREATE_AUTH
import com.chillieman.chilliewallet.definitions.UtilDefinitions.ONE_SECOND
import com.chillieman.chilliewallet.manager.AuthManager
import com.chillieman.chilliewallet.model.AuthStatus
import com.chillieman.chilliewallet.ui.auth.AuthActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class AuthService : BaseService() {
    private val binder = AuthBinder()

    private var lockoutDisposable: Disposable? = null

    @Inject
    lateinit var authManager: AuthManager

    // Start tracking the Time. Reset
//    private val unlockDuration = ONE_MINUTE * 5
    private val unlockDuration = ONE_SECOND * 15
    var timeWhenUnlocked = Calendar.getInstance().timeInMillis

    private fun startLockoutTimer() {
        lockoutDisposable = Single.create<AuthStatus> {
            while(true) {
                if(Calendar.getInstance().timeInMillis > timeWhenUnlocked + unlockDuration) {
                    //User has timed out!
                    it.onSuccess(AuthStatus.UNAUTHENTICATED)
                    return@create
                }

                //Wait 5 seconds before checking the Auth Lock again.
                Thread.sleep(5000)
            }
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authManager.setAuthenticationStatus(it)
            }, {
                Log.e(TAG, "Error with the AuthService - Logout Timer")
            })
    }

    private fun stopLockoutTimer() {
        lockoutDisposable?.dispose()
    }

    override fun onCreate() {
        super.onCreate()
        //Check first to see if the AUTH Record has already been created.
        authManager.isAuthCreated()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it) {
                    //Its already Created
                    authManager.setAuthenticationStatus(AuthStatus.UNAUTHENTICATED)
                } else {
                    //It needs to be created!
                    authManager.setAuthenticationStatus(AuthStatus.NEED_TO_CREATE)
                }

            }, {
                Log.e(TAG, "Error with the AuthService - Logout Timer")
            }).disposeOnDestroy()

        //Set the Status of the AuthManager here

        authManager.authStatus.observeForever {
            when(it) {
                AuthStatus.NEED_TO_CREATE -> {
                    startActivity(
                        Intent(this, AuthActivity::class.java)
                            .putExtra(EXTRA_NEED_TO_CREATE_AUTH, true)
                            .addFlags(FLAG_ACTIVITY_NEW_TASK)
                    )
                }
                AuthStatus.UNAUTHENTICATED -> {
                    //Stop Monitoring the Auth Status since now you are logged out
                    stopLockoutTimer()
                    startActivity(
                        Intent(this, AuthActivity::class.java)
                            .addFlags(FLAG_ACTIVITY_NEW_TASK)
                    )
                }
                AuthStatus.AUTHENTICATED -> {
                    timeWhenUnlocked = Calendar.getInstance().timeInMillis
                    startLockoutTimer()
                    // START THE AUTH CHECKER!
                }
                else -> Unit
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand!")
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        //The user just stopped the application - Send them a notifcation that Orders wont work
        Log.d(TAG, "Task has been removed!")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLockoutTimer()
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class AuthBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): AuthService = this@AuthService
    }

    companion object {
        private const val TAG = "ChillieAuthService"
    }
}