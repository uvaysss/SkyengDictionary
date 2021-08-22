package com.uvaysss.skyengdictionary.di

import com.uvaysss.skyengdictionary.di.module.AndroidModule
import com.uvaysss.skyengdictionary.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by uvays on 22.08.2021.
 */

@Singleton
@Component(modules = [AndroidModule::class, NetworkModule::class])
interface AppComponent {
}