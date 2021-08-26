package com.uvaysss.skyengdictionary.ui.wordlist

import com.uvaysss.skyengdictionary.data.model.word.Word
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by uvays on 24.08.2021.
 */

interface WordListMvpView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setLoading(value: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setRefreshing(value: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setContent(value: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setEmpty(value: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setItems(value: List<Word>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addItems(value: List<Word>)
}