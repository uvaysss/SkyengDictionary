package com.uvaysss.skyengdictionary.ui.worddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.stfalcon.imageviewer.StfalconImageViewer
import com.uvaysss.skyengdictionary.App
import com.uvaysss.skyengdictionary.R
import com.uvaysss.skyengdictionary.data.model.word.Word
import com.uvaysss.skyengdictionary.data.model.word.WordMeaningImage
import com.uvaysss.skyengdictionary.databinding.FragmentWordDetailBinding
import com.uvaysss.skyengdictionary.databinding.ItemWordDetailImageBinding
import com.uvaysss.skyengdictionary.ui.core.navigation.BaseFragment
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.android.parcel.Parcelize
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@Parcelize
data class WordDetailKey(val id: Long) : DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = WordDetailFragment()
}

class WordDetailFragment : BaseFragment(), WordDetailMvpView {

    private var _binding: FragmentWordDetailBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var presenterProvider: Provider<WordDetailPresenter>

    private val presenter: WordDetailPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var imagesAdapter: AsyncListDifferDelegationAdapter<WordMeaningImage>

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = getKey<WordDetailKey>()

        binding.collapsingToolbarLayout.applySystemWindowInsetsToPadding(consume = false)
        binding.toolbar.applySystemWindowInsetsToPadding(top = true)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.nestedScrollView.applySystemWindowInsetsToPadding(bottom = true)

        imagesAdapter = AsyncListDifferDelegationAdapter(
            object : DiffUtil.ItemCallback<WordMeaningImage>() {
                override fun areItemsTheSame(
                    oldItem: WordMeaningImage,
                    newItem: WordMeaningImage
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: WordMeaningImage,
                    newItem: WordMeaningImage
                ): Boolean = oldItem == newItem
            },
            createImageAdapterDelegate { item, startPosition ->
                val images = imagesAdapter.items.toTypedArray()

                StfalconImageViewer
                    .Builder(requireActivity(), images) { imageView, image ->
                        Glide
                            .with(this)
                            .load(image.url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(imageView)
                    }
                    .withImageChangeListener { position ->
                        binding.viewPagerImages.setCurrentItem(position, false)
                    }
                    .withStartPosition(startPosition)
                    .withBackgroundColorResource(R.color.black)
                    .withHiddenStatusBar(false)
                    .allowZooming(true)
                    .allowSwipeToDismiss(true)
                    .show()
            }
        )

        binding.viewPagerImages.adapter = imagesAdapter

        binding.scrollingPagerIndicator.attachToPager(binding.viewPagerImages)

        presenter.onStart(key.id)
    }

    private fun createImageAdapterDelegate(onClick: (WordMeaningImage, Int) -> Unit) =
        adapterDelegateViewBinding<WordMeaningImage, WordMeaningImage, ItemWordDetailImageBinding>(
            { inflater, parent -> ItemWordDetailImageBinding.inflate(inflater, parent, false) }
        ) {
            binding.imageView.setOnClickListener {
                onClick(item, adapterPosition)
            }

            bind {
                Glide
                    .with(itemView)
                    .load(item.url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageView)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setData(value: Word) {
        binding.textViewTitle.text = value.title
        binding.textViewSubtitle.text = value.subtitle

        if (value.images.isEmpty()) {
            binding.headerView.isVisible = false
        } else {
            imagesAdapter.items = value.images
            imagesAdapter.notifyDataSetChanged()

            binding.headerView.isVisible = true
        }
    }
}