package com.uvaysss.skyengdictionary.data.repository

import com.uvaysss.skyengdictionary.data.mapper.WordResponseToModelMapper
import com.uvaysss.skyengdictionary.data.model.word.Word
import com.uvaysss.skyengdictionary.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by uvays on 23.08.2021.
 */

class WordRepository @Inject constructor(
    private val apiService: ApiService,
    private val wordResponseToModelMapper: WordResponseToModelMapper
) {

    companion object {
        private const val PAGE_LIMIT = 20
    }

    private val wordMap = mutableMapOf<Long, Word>()

    suspend fun getWords(search: String, page: Int = 0): List<Word> = withContext(Dispatchers.IO) {
        val response = apiService.getWordList(
            search = search,
            page = page,
            limit = PAGE_LIMIT
        )

        val words = response.map(wordResponseToModelMapper)
        val map = words.associateBy { word -> word.id }

        if (page == 0) {
            wordMap.clear()
        }

        wordMap.putAll(map)

        return@withContext words
    }

    suspend fun getWord(id: Long): Word? = withContext(Dispatchers.IO) {
        return@withContext wordMap[id]
    }
}