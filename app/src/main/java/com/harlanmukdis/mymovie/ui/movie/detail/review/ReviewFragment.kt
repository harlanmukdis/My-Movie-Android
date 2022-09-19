package com.harlanmukdis.mymovie.ui.movie.detail.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.harlanmukdis.mymovie.MyMovieApp
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.FragmentReviewBinding
import com.harlanmukdis.mymovie.ui.LoadingAdapter
import com.harlanmukdis.mymovie.ui.movie.detail.review.adapter.ReviewPageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewFragment: Fragment() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private val app = MyMovieApp()
    private lateinit var binding: FragmentReviewBinding
    private val viewModel: ReviewViewModel by viewModel()
    private lateinit var reviewPageAdapter: ReviewPageAdapter
    private var movieID = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initReviewsByMovie()

            arguments?.also {
                movieID = it.getInt(EXTRA_DATA)

                lifecycleScope.launchWhenCreated {
                    reviewPageAdapter.loadStateFlow.collect { state ->
                        when {
                            state.source.refresh is LoadState.Loading ->
                                showShimmer(true)
                            reviewPageAdapter.itemCount < 1 -> {
                                showShimmer(false)
                                stateContent(
                                    false,
                                    "No review data to show"
                                )
                            }
                            else -> {
                                showShimmer(false)
                                stateContent(false)
                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.getReviewsByMovie(movieID).collectLatest {
                        reviewPageAdapter.submitData(it)
                    }
                }
            }

            binding.layoutContent.btnRetry.setOnClickListener {
                if (!app.isNetworkAvailable(requireActivity())) {
                    stateContent(true, getString(R.string.message_no_connection))
                } else {
                    stateContent(false)
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.getReviewsByMovie(movieID).collectLatest {
                            reviewPageAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun initReviewsByMovie() {
        reviewPageAdapter = ReviewPageAdapter()
        val loadingAdapter = LoadingAdapter()
        val linearLayoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        with(binding.reviews) {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = reviewPageAdapter.withLoadStateFooter(loadingAdapter)
        }
    }

    private fun showShimmer(isShow: Boolean) {
        if (isShow) {
            binding.shimmerReview.startShimmer()
            binding.shimmerReview.visibility = View.VISIBLE
            binding.reviews.visibility = View.INVISIBLE
        } else {
            binding.shimmerReview.startShimmer()
            binding.shimmerReview.visibility = View.GONE
            binding.reviews.visibility = View.VISIBLE
        }
    }

    private fun stateContent(
        isCanRetry: Boolean,
        message: String? = null
    ) {
        if (isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.reviews.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else if (!isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.reviews.visibility = View.GONE
            binding.layoutContent.btnRetry.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else {
            binding.layoutContent.content.visibility = View.GONE
            binding.reviews.visibility = View.VISIBLE
        }
    }
}