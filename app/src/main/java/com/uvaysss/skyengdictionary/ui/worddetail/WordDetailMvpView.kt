package com.uvaysss.skyengdictionary.ui.worddetail

import com.uvaysss.skyengdictionary.data.model.word.Word
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by uvays on 24.08.2021.
 */

interface WordDetailMvpView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(value: Word)
}