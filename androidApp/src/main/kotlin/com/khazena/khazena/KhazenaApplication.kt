package com.khazena.khazena

import android.app.Application
import com.khazena.khazena.di.initKoin
import org.koin.android.ext.koin.androidContext

class KhazenaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KhazenaApplication)
        }
    }
}
