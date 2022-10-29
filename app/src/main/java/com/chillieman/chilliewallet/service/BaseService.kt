package com.chillieman.chilliewallet.service

import android.content.Intent
import android.os.IBinder
import dagger.android.AndroidInjection
import dagger.android.DaggerService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseService : DaggerService() {
    private val disposables = CompositeDisposable()

    protected fun <T : Disposable> T.disposeOnDestroy(): T {
        disposables.add(this)
        return this
    }

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
