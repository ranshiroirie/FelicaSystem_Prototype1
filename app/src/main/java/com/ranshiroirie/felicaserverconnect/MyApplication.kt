package com.ranshiroirie.felicaserverconnect

import android.app.Application

class MyApplication : Application() {
    companion object {

        lateinit var instance: Application private set

    }

    override fun onCreate() {
        super.onCreate()

        instance = this

    }
}