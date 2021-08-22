package com.uvaysss.skyengdictionary.data.network.word

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by uvays on 22.08.2021.
 */

@JsonClass(generateAdapter = true)
data class WordResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "text") val text: String,
    @Json(name = "meanings") val meanings: List<WordMeaningResponse>
)

@JsonClass(generateAdapter = true)
data class WordMeaningResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "partOfSpeechCode") val partOfSpeechCode: String,
    @Json(name = "translation") val translation: WordMeaningTranslationResponse,
    @Json(name = "transcription") val transcription: String,
    @Json(name = "previewUrl") val previewUrl: String?,
    @Json(name = "imageUrl") val imageUrl: String?,
)

@JsonClass(generateAdapter = true)
data class WordMeaningTranslationResponse(
    @Json(name = "text") val text: String,
    @Json(name = "note") val note: String?
)