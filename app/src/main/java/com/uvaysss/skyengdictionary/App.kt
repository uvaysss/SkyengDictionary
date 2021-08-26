package com.uvaysss.skyengdictionary

import android.app.Application
import com.uvaysss.skyengdictionary.di.AppComponent
import com.uvaysss.skyengdictionary.di.DaggerAppComponent
import com.uvaysss.skyengdictionary.di.module.AndroidModule
import com.uvaysss.skyengdictionary.di.module.DataModule
import com.uvaysss.skyengdictionary.di.module.NetworkModule
import timber.log.Timber

/**
 * Created by uvays on 23.08.2021.
 */

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    init {
        instance = this
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .androidModule(AndroidModule(this))
            .networkModule(NetworkModule())
            .dataModule(DataModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}