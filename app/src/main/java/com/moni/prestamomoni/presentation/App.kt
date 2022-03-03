package com.moni.prestamomoni.presentation

import android.app.Application
import com.moni.prestamomoni.BuildConfig
import com.moni.prestamomoni.di.moni_module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(moni_module)
        }
    }
}