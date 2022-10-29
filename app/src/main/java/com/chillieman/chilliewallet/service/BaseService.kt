package com.chillieman.chilliewallet.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseService : Service() {
    private val disposables = CompositeDisposable()

    protected fun <T : Disposable> T.disposeOnDestroy(): T {
        disposables.add(this)
        return this
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
