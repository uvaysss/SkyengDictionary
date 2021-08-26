package com.uvaysss.skyengdictionary.ui.wordlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.uvaysss.skyengdictionary.App
import com.uvaysss.skyengdictionary.R
import com.uvaysss.skyengdictionary.data.model.word.Word
import com.uvaysss.skyengdictionary.databinding.FragmentWordListBinding
import com.uvaysss.skyengdictionary.databinding.ItemWordBinding
import com.uvaysss.skyengdictionary.ui.core.EndlessScrollListener
import com.uvaysss.skyengdictionary.ui.worddetail.WordDetailKey
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.android.parcel.Parcelize
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@Parcelize
data class WordListKey(private val placeholder: Int = 0) : DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = WordListFragment()
}

class WordListFragment : MvpAppCompatFragment(), WordListMvpView {

    private var _binding: FragmentWordListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var presenterProvider: Provider<WordListPresenter>

    private val presenter: WordListPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var adapter: AsyncListDifferDelegationAdapter<Word>
    private lateinit var endlessScrollListener: EndlessScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.applySystemWindowInsetsToPadding(top = true)

        val searchView = binding.toolbar.menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = resources.getString(R.string.action_search)
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.onSearchSubmit(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onSearchTextChange(newText)
                return false
            }
        })

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.accent)
        binding.swipeRefreshLayout.setOnRefreshListener {
            presenter.onRefresh()
        }

        adapter = AsyncListDifferDelegationAdapter(
            object : DiffUtil.ItemCallback<Word>() {
                override fun areItemsTheSame(
                    oldItem: Word,
                    newItem: Word
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Word,
                    newItem: Word
                ): Boolean = oldItem == newItem
            },
            createWordAdapterDelegate { word, position ->
                backstack.goTo(WordDetailKey(word.id))
            }
        )

        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        endlessScrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.onLoadMore(page)
            }
        }

        binding.recyclerView.applySystemWindowInsetsToPadding(bottom = true)
        binding.recyclerView.addOnScrollListener(endlessScrollListener)
        binding.recyclerView.adapter = adapter
    }

    private fun createWordAdapterDelegate(onClick: (Word, Int) -> Unit) =
        adapterDelegateViewBinding<Word, Word, ItemWordBinding>(
            { inflater, parent -> ItemWordBinding.inflate(inflater, parent, false) }
        ) {
            binding.textView.setOnClickListener {
                onClick(item, adapterPosition)
            }

            bind {
                binding.textView.text = item.title
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * WordListMvpView
     */

    override fun setLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    override fun setRefreshing(value: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = value
    }

    override fun setContent(value: Boolean) {
        binding.recyclerView.isVisible = value
    }

    override fun setEmpty(value: Boolean) {
        binding.viewEmpty.isVisible = value
    }

    override fun setItems(value: List<Word>) {
        binding.recyclerView.post {
            adapter.items = value
            endlessScrollListener.resetState()
        }
    }

    override fun addItems(value: List<Word>) {
        val newItems = adapter.items.toMutableList()
        newItems.addAll(value)

        binding.recyclerView.post {
            adapter.items = newItems
        }
    }
}