package com.chillieman.chilliewallet.repository

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepository
@Inject constructor() {
    fun sayHi() {
        Log.d("ChillieMockRepo", "Well Hello there!")
    }
}