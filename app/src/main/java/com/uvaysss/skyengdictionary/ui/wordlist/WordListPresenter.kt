package com.uvaysss.skyengdictionary.ui.wordlist

import com.uvaysss.skyengdictionary.data.repository.WordRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by uvays on 24.08.2021.
 */

class WordListPresenter @Inject constructor(
    private val wordRepository: WordRepository
) : MvpPresenter<WordListMvpView>() {

    private var fetchDataJob: Job? = null
    private var search: String = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setLoading(false)
        viewState.setContent(false)
        viewState.setEmpty(true)
        viewState.setRefreshing(false)
        viewState.setItems(emptyList())
    }

    fun onSearchSubmit(text: String) {
        search = text
        fetchData()
    }

    fun onSearchTextChange(text: String) {
        if(text.isEmpty()) {
            return
        }

        if (text.length < 2) {
            viewState.setItems(emptyList())
            return
        }

        search = text
        fetchData()
    }

    fun onRefresh() {
        fetchData()
    }

    fun onLoadMore(page: Int) {
        fetchData(page)
    }

    private fun fetchData() {
        fetchDataJob?.cancel()
        fetchDataJob = presenterScope.launch {
            viewState.setLoading(true)
            viewState.setContent(false)
            viewState.setRefreshing(false)

            try {
                val items = wordRepository.getWords(search = search)

                viewState.setLoading(false)
                viewState.setContent(items.isNotEmpty())
                viewState.setEmpty(items.isEmpty())
                viewState.setRefreshing(false)
                viewState.setItems(items)
            } catch (e: Exception) {
                Timber.e(e)

                viewState.setLoading(false)
                viewState.setContent(false)
                viewState.setRefreshing(false)
            }
        }
    }

    private fun fetchData(page: Int) {
        fetchDataJob?.cancel()
        fetchDataJob = presenterScope.launch {
            viewState.setRefreshing(true)

            try {
                val items = wordRepository.getWords(search = search, page = page)

                viewState.setRefreshing(false)
                viewState.addItems(items)
            } catch (e: Exception) {
                Timber.e(e)

                viewState.setRefreshing(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        fetchDataJob?.cancel()
    }
}