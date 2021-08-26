package com.uvaysss.skyengdictionary.ui.worddetail

import com.uvaysss.skyengdictionary.data.repository.WordRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

/**
 * Created by uvays on 24.08.2021.
 */

class WordDetailPresenter @Inject constructor(
    private val wordRepository: WordRepository
) : MvpPresenter<WordDetailMvpView>() {

    private var getWordJob: Job? = null

    fun onStart(id: Long) {
        getWordJob?.cancel()
        getWordJob = presenterScope.launch {
            val word = wordRepository.getWord(id) ?: return@launch

            viewState.setData(word)
        }
    }
}