package com.uvaysss.skyengdictionary.di.module

import com.uvaysss.skyengdictionary.data.mapper.WordResponseToModelMapper
import com.uvaysss.skyengdictionary.data.network.ApiService
import com.uvaysss.skyengdictionary.data.repository.WordRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by uvays on 25.08.2021.
 */

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideWordRepository(
        apiService: ApiService, wordResponseToModelMapper: WordResponseToModelMapper
    ) : WordRepository {
        return WordRepository(apiService, wordResponseToModelMapper)
    }
}