package com.harlanmukdis.mymovie.ui.movie.list

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.mymovie.MyMovieApp
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.FragmentMovieBinding
import com.harlanmukdis.mymovie.ui.movie.detail.MovieDetailActivity
import com.harlanmukdis.mymovie.ui.LoadingAdapter
import com.harlanmukdis.mymovie.ui.movie.list.adapter.MoviePageAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesFragment : Fragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null

    private val binding get() = _binding!!
    private var genre = Genre()
    private val viewModel: MovieVieModel by viewModel()
    private lateinit var moviePageAdapter: MoviePageAdapter
    private val app = MyMovieApp()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initMoviesByGenre()

            arguments?.also {
                genre = it.getSerializable("genre") as Genre

                (activity as AppCompatActivity?)!!.supportActionBar!!.title = genre.name

                if (!app.isNetworkAvailable(requireActivity())) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.message_no_connection),
                        Toast.LENGTH_LONG
                    ).show()
                    stateContent(true, getString(R.string.message_no_connection))
                } else {
                    lifecycleScope.launchWhenCreated {
                        moviePageAdapter.loadStateFlow.collect { state ->
                            when {
                                state.source.refresh is LoadState.Loading ->
                                    showShimmer(true)
                                moviePageAdapter.itemCount < 1 -> {
                                    showShimmer(false)
                                    stateContent(
                                        false,
                                        "No movies data to show"
                                    )
                                }
                                else -> {
                                    showShimmer(false)
                                    stateContent(false)
                                }
                            }
                        }
                    }

                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getMoviesByGenre(genre.id!!).collectLatest {
                            moviePageAdapter?.submitData(it)
                        }
                    }
                }
            }

            binding.swMovies.setOnRefreshListener {
                binding.swMovies.isRefreshing = false
                viewModel.getMoviesByGenre(genre.id!!)
            }

            binding.layoutContent.btnRetry.setOnClickListener {
                if (!app.isNetworkAvailable(requireActivity())) {
                    stateContent(true, getString(R.string.message_no_connection))
                } else {
                    stateContent(false)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getMoviesByGenre(genre.id!!).collectLatest {
                            moviePageAdapter?.submitData(it)
                        }
                    }
                }
            }
        }
    }

    private fun showShimmer(isShow: Boolean) {
        with(binding) {
            if (isShow) {
                shimmerMovie.startShimmer()
                shimmerMovie.visibility = View.VISIBLE
            } else {
                shimmerMovie.startShimmer()
                shimmerMovie.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMoviesByGenre() {
        moviePageAdapter = MoviePageAdapter()
        moviePageAdapter?.onItemClickListener = {
            navigateToDetail(it)
        }
        val loadingAdapter = LoadingAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == moviePageAdapter?.itemCount && loadingAdapter.itemCount > 0)
                    2
                else
                    1
            }
        }
        val decor = GridSpacingItemDecoration(2, dpToPx(10), true)
        with(binding.movies) {
            layoutManager = gridLayoutManager
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(decor)
            adapter = moviePageAdapter?.withLoadStateFooter(loadingAdapter)
        }
    }

    private fun navigateToDetail(data: Movie) {
        val bundle = Bundle()
        bundle.putSerializable("movie", data)
        val intent = Intent(requireActivity(), MovieDetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun stateContent(
        isCanRetry: Boolean,
        message: String? = null
    ) {
        if (isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.movies.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else if (!isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.movies.visibility = View.GONE
            binding.layoutContent.btnRetry.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else {
            binding.layoutContent.content.visibility = View.GONE
            binding.movies.visibility = View.VISIBLE
        }
    }

    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column
            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }
}