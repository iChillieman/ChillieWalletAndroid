package com.chillieman.chilliewallet.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransactionService : BaseService() {
    private val binder = TransactionBinder()

    //Create a Transaction Manager that can do your transfers and stuff!

    override fun onCreate() {
        super.onCreate()
        Single.fromCallable {
            Log.d(TAG, "YAWN")
            Thread.sleep(5000)
            Log.d(TAG, "YAWN")
            Thread.sleep(5000)
            Log.d(TAG, "YAWWWWWWWWWWWWWWWWWWWWWN")
            Thread.sleep(20000)
        }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "CREATED - That was a nice nap")
            }, {
                Log.e(TAG, "Error taking a nap", it)
            }).disposeOnDestroy()

        Log.d(TAG, "CREATED - Time To sleep")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand!")
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        //The user just stopped the application - Send them a notification that Orders wont work when the application is not running!
        Log.d(TAG, "Task has been removed!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service has been destroyed!")
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class TransactionBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService() = this@TransactionService
    }

    companion object {
        private const val TAG = "ChillieTransactionService"
    }
}