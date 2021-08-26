package com.uvaysss.skyengdictionary.data.mapper

import com.uvaysss.skyengdictionary.data.network.word.WordMeaningResponse
import com.uvaysss.skyengdictionary.data.network.word.WordMeaningTranslationResponse
import com.uvaysss.skyengdictionary.data.network.word.WordResponse
import org.junit.Test

/**
 * Created by uvays on 26.08.2021.
 */

class WordResponseToModelMapperTest {

    @Test
    fun test() {
        val response = WordResponse(
            id = 10L,
            text = "operation",
            meanings = listOf(
                WordMeaningResponse(
                    id = 100L,
                    partOfSpeechCode = "n",
                    translation = WordMeaningTranslationResponse(
                        text = "операция",
                        note = null
                    ),
                    transcription = "ˌɒpəˈreɪʃən",
                    previewUrl = null,
                    imageUrl = "//d2zkmv5t5kao9.cloudfront.net/images/7c33a28e24386df690c2ca7caecde020.jpeg?w=640&h=480"
                ),
                WordMeaningResponse(
                    id = 200L,
                    partOfSpeechCode = "n",
                    translation = WordMeaningTranslationResponse(
                        text = "действие",
                        note = null
                    ),
                    transcription = "ˌɒpəˈreɪʃən",
                    previewUrl = null,
                    imageUrl = "//d2zkmv5t5kao9.cloudfront.net/images/28522d96717bdf22d080800eda25034f.png?w=640&h=480"
                )
            )
        )

        val mapper = WordResponseToModelMapper()
        val model = mapper(response)

        assert(model.id == 10L)
        assert(model.title == "operation")
        assert(model.subtitle == "операция, действие")
        assert(model.images[0].id == 100L)
        assert(model.images[0].url == "https://d2zkmv5t5kao9.cloudfront.net/images/7c33a28e24386df690c2ca7caecde020.jpeg?w=640&h=480")
        assert(model.images[1].id == 200L)
        assert(model.images[1].url == "https://d2zkmv5t5kao9.cloudfront.net/images/28522d96717bdf22d080800eda25034f.png?w=640&h=480")
    }
}