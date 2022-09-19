package com.harlanmukdis.mymovie.ui.movie.detail.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.harlanmukdis.core.data.source.remote.response.VideoResponse
import com.harlanmukdis.mymovie.MyMovieApp
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.FragmentVideoBinding
import com.harlanmukdis.mymovie.ui.movie.detail.about.AboutFragment
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class VideoFragment: Fragment() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: FragmentVideoBinding
    private val viewModel: VideoViewModel by viewModel()
    private val app = MyMovieApp()
    private var movieID: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        arguments?.also {
            movieID = it.getInt(EXTRA_DATA)

            if (!app.isNetworkAvailable(requireActivity())) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.message_no_connection),
                    Toast.LENGTH_LONG
                ).show()
                stateContent(true, getString(R.string.message_no_connection))
            } else {
                stateContent(false)
                viewModel.getVideos(movieID)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            lifecycleScope.launch {
                viewModel.loadingState.collect {
                    showShimmer(it)
                }
            }

            lifecycleScope.launch {
                viewModel.errorMsgState.collect {
                    if (it.isNotEmpty()) {
                        stateContent(true, it)
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.emptyMsgState.collect {
                    if (it.isNotEmpty()) {
                        stateContent(false, it)
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.videosState.collect {
                    if (it.isNotEmpty()) {
                        stateContent(false)
                        initVideos(it)
                    }
                }
            }

            binding.layoutContent.btnRetry.setOnClickListener {
                if (!app.isNetworkAvailable(requireActivity())) {
                    stateContent(true, getString(R.string.message_no_connection))
                } else {
                    stateContent(false)
                    viewModel.getVideos(movieID)
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

    private fun initVideos(listData: List<VideoResponse>) {
        val videoAdapter = VideoAdapter()
        videoAdapter.setData(listData)
        with(binding.videos) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = videoAdapter
        }
    }

    private fun showShimmer(isShow: Boolean) {
        if (isShow) {
            binding.shimmerVideo.startShimmer()
            binding.shimmerVideo.visibility = View.VISIBLE
            binding.videos.visibility = View.INVISIBLE
        } else {
            binding.shimmerVideo.startShimmer()
            binding.shimmerVideo.visibility = View.GONE
            binding.videos.visibility = View.VISIBLE
        }
    }

    private fun stateContent(
        isCanRetry: Boolean,
        message: String? = null
    ) {
        if (isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.videos.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else if (!isCanRetry && !message.isNullOrEmpty()) {
            binding.layoutContent.content.visibility = View.VISIBLE
            binding.videos.visibility = View.GONE
            binding.layoutContent.btnRetry.visibility = View.GONE
            binding.layoutContent.tvError.text = message
        } else {
            binding.layoutContent.content.visibility = View.GONE
            binding.videos.visibility = View.VISIBLE
        }
    }
}