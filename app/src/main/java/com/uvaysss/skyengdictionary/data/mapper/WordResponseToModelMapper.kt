package com.uvaysss.skyengdictionary.data.mapper

import com.uvaysss.skyengdictionary.data.model.word.Word
import com.uvaysss.skyengdictionary.data.model.word.WordMeaningImage
import com.uvaysss.skyengdictionary.data.network.word.WordResponse
import javax.inject.Inject

/**
 * Created by uvays on 24.08.2021.
 */

class WordResponseToModelMapper @Inject constructor() : (WordResponse) -> Word {

    override fun invoke(response: WordResponse): Word {
        val subtitle = response.meanings.joinToString(
            separator = ", ",
            transform = { meaning -> meaning.translation.text }
        )

        val images = response.meanings
            .filter { meaning -> meaning.imageUrl != null }
            .map { meaning -> WordMeaningImage(meaning.id, "https:${meaning.imageUrl}") }

        return Word(
            id = response.id,
            title = response.text,
            subtitle = subtitle,
            images = images
        )
    }
}