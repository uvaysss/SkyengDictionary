package com.uvaysss.skyengdictionary.data.model.word

/**
 * Created by uvays on 24.08.2021.
 */

data class Word(
    val id: Long,
    val title: String,
    val subtitle: String,
    val images: List<WordMeaningImage>
)

data class WordMeaningImage(
    val id: Long,
    val url: String
)