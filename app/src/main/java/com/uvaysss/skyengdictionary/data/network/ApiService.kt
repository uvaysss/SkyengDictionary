package com.uvaysss.skyengdictionary.data.network

import com.uvaysss.skyengdictionary.data.network.word.WordResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by uvays on 22.08.2021.
 */

interface ApiService {

    @GET("/api/public/v1/words/search")
    suspend fun getWordList(
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("pageSize") limit: Int
    ) : List<WordResponse>
}