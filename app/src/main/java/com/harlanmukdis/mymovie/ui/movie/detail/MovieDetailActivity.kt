package com.harlanmukdis.mymovie.ui.movie.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.harlanmukdis.core.data.source.remote.response.MovieDetailResponse
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.mymovie.ui.movie.detail.about.AboutFragment
import com.harlanmukdis.mymovie.MyMovieApp
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ActivityMovieDetailBinding
import com.harlanmukdis.mymovie.ui.movie.detail.review.ReviewFragment
import com.harlanmukdis.mymovie.ui.movie.detail.video.VideoFragment
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.abs

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModel()
    private val app = MyMovieApp()
    private var movie = Movie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_movie_detail
        )

        intent?.extras?.also {
            movie = it.getSerializable("movie") as Movie
        }

        if (!app.isNetworkAvailable(this)) {
            Toast.makeText(
                applicationContext,
                getString(R.string.message_no_connection),
                Toast.LENGTH_LONG
            ).show()
            stateContent(true, getString(R.string.message_no_connection))
        } else {
            stateContent(false)
            viewModel.getMovieDetail(movie.id)
        }

        lifecycleScope.launch {
            viewModel.loadingState.collect {

            }
        }

        viewModel.movieDetailState.observe(this) {
            with(binding) {
                source = it

                initCollapsingToolbar(it)
                initViewPager()
            }
        }

        binding.layoutContent.btnRetry.setOnClickListener {
            if (!app.isNetworkAvailable(this)) {
                stateContent(true, getString(R.string.message_no_connection))
            } else {
                stateContent(false)
                viewModel.getMovieDetail(movie.id)
            }
        }
    }

    private fun initCollapsingToolbar(movie: MovieDetailResponse) {
        val year = movie.release_date.split("-".toRegex()).toTypedArray()

        setSupportActionBar(binding.toolbar)
        binding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.purple_500))
        binding.collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white))
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {

            @SuppressLint("SetTextI18n")
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.COLLAPSED) {
                    try {
                        binding.toolbar.title = "${movie.original_title} (${year[0]})"
                        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                        supportActionBar!!.setHomeButtonEnabled(true)
                        supportActionBar!!.setBackgroundDrawable(
                            ColorDrawable(
                            ContextCompat.getColor(this@MovieDetailActivity, R.color.purple_500))
                        )
                        binding.collapsingToolbar.title = "${movie.original_title} (${year[0]})"
                        binding.title.visibility = View.GONE
                        binding.subTitle.visibility = View.GONE
                        binding.genre.visibility = View.GONE
                        binding.overview.visibility = View.GONE
                        supportActionBar?.setDisplayShowTitleEnabled(true)
                    } catch (e: Exception) {
                        Log.e(MovieDetailActivity::class.java.simpleName,  "${e.printStackTrace()}")
                    }
                } else if (state == State.EXPANDED) {
                    binding.toolbar.title = ""
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    supportActionBar!!.setHomeButtonEnabled(false)
                    supportActionBar!!.setBackgroundDrawable(
                        ColorDrawable(
                        ContextCompat.getColor(this@MovieDetailActivity, R.color.color_transparent))
                    )
                    binding.collapsingToolbar.title = ""
                    binding.collapsingToolbar.setExpandedTitleColor(
                        ContextCompat.getColor(this@MovieDetailActivity, R.color.color_transparent)
                    )
                    binding.title.visibility = View.VISIBLE
                    binding.overview.visibility = View.VISIBLE
                    binding.title.text = "${movie.original_title} (${year[0]})"
                    binding.subTitle.visibility = View.VISIBLE
                    binding.genre.visibility = View.VISIBLE
                    var genres = ""
                    for (i in movie.genres.indices) {
                        if (i > 1) {
                            genres += ", ${movie.genres[i].name}"
                        } else {
                            genres = movie.genres[i].name
                        }
                        binding.genre.text = genres
                    }
                    val iso3166 = if (!movie.production_countries.isNullOrEmpty()) {
                        movie.production_countries[0].iso_3166_1
                    } else {
                        ""
                    }
                    binding.subTitle.text = "${movie.release_date} ($iso3166)"
                    binding.overview.apply {
                        setShowingLine(3)
                        setShowLessTextColor(Color.BLUE)
                        setShowMoreTextColor(Color.BLUE)
                        addShowLessText("Show less")
                        addShowMoreText("Show more")
                    }
                }
            }
        })
    }

    abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
        enum class State {
            EXPANDED, COLLAPSED, IDLE
        }

        private var mCurrentState = State.IDLE
        override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
            mCurrentState = when {
                i == 0 -> {
                    if (mCurrentState != State.EXPANDED) {
                        onStateChanged(appBarLayout, State.EXPANDED)
                    }
                    State.EXPANDED
                }
                abs(i) >= appBarLayout.totalScrollRange -> {
                    if (mCurrentState != State.COLLAPSED) {
                        onStateChanged(appBarLayout, State.COLLAPSED)
                    }
                    State.COLLAPSED
                }
                else -> {
                    if (mCurrentState != State.IDLE) {
                        onStateChanged(appBarLayout, State.IDLE)
                    }
                    State.IDLE
                }
            }
        }

        abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State?)
    }

    private fun initViewPager() {
        val bundle = Bundle()
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        bundle.putInt(AboutFragment.EXTRA_DATA, movie.id)
        val about = AboutFragment()
        about.arguments = bundle
        pagerAdapter.addFragment(about, "About")
        val video = VideoFragment()
        video.arguments = bundle
        pagerAdapter.addFragment(video, "Trailer")
        bundle.putInt(ReviewFragment.EXTRA_DATA, movie.id)
        val review = ReviewFragment()
        review.arguments = bundle
        pagerAdapter.addFragment(review, "Review")
        with(binding.viewPager) {
            adapter = pagerAdapter
        }
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun stateContent(
        isCanRetry: Boolean,
        message: String? = null
    ) {
        if (isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.layoutContent.tvError.text = message
        } else if (!isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.layoutContent.btnRetry.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else {
            binding.layoutContent.content.visibility = View.GONE
        }
    }

}