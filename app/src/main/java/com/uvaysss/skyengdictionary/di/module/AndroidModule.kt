package com.uvaysss.skyengdictionary.di.module

import android.content.Context
import android.content.res.Resources
import com.uvaysss.skyengdictionary.App
import dagger.Module
import dagger.Provides

/**
 * Created by uvays on 22.08.2021.
 */

@Module
class AndroidModule(private val application: App) {

    @Provides
    fun provideApp(): App = application

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    fun provideResources(): Resources = application.resources
}