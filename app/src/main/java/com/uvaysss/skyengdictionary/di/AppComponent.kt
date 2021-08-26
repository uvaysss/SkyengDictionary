package com.uvaysss.skyengdictionary.di

import com.uvaysss.skyengdictionary.di.module.AndroidModule
import com.uvaysss.skyengdictionary.di.module.DataModule
import com.uvaysss.skyengdictionary.di.module.NetworkModule
import com.uvaysss.skyengdictionary.ui.worddetail.WordDetailFragment
import com.uvaysss.skyengdictionary.ui.wordlist.WordListFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by uvays on 22.08.2021.
 */

@Singleton
@Component(modules = [AndroidModule::class, NetworkModule::class, DataModule::class])
interface AppComponent {
    fun inject(wordListFragment: WordListFragment)
    fun inject(wordDetailFragment: WordDetailFragment)
}