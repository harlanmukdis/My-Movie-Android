package com.harlanmukdis.mymovie.ui.genre

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.mymovie.MyMovieApp
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.FragmentGenreBinding
import com.harlanmukdis.mymovie.ui.MainActivity
import com.harlanmukdis.mymovie.ui.movie.detail.MovieDetailActivity
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class GenresFragment : Fragment() {
    private var _binding: FragmentGenreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: GenreViewModel by viewModel()
    private lateinit var genreAdapter: GenreAdapter
    private val app = MyMovieApp()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!app.isNetworkAvailable(requireActivity()))
            Toast.makeText(
                requireActivity(),
                getString(R.string.message_no_connection),
                Toast.LENGTH_LONG
            ).show()

        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.loadingState.collect {
                showShimmer(it)
            }
        }

        genreAdapter = GenreAdapter()
        lifecycleScope.launch {
            viewModel.genresState.collect {
                if (it.isNotEmpty())
                    genreAdapter.setData(it)
            }
        }
        with(binding.rvGenre) {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            itemAnimator = DefaultItemAnimator()
            adapter = genreAdapter
        }
        genreAdapter.setOnClickListener(object : GenreAdapter.GenreAdapterCallback {
            override fun onViewAllClick(data: Genre) {
                val bundle = Bundle()
                bundle.putSerializable("genre", data)
                findNavController().navigate(R.id.action_GenreFragment_to_MovieFragment, bundle)
            }

            override fun onThumbClick(data: Movie) {
                val bundle = Bundle()
                bundle.putSerializable("movie", data)
                val intent = Intent(requireActivity(), MovieDetailActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })

        lifecycleScope.launch {
            viewModel.errorMsgState.collect {
                if (it.isNotEmpty()) {
                    Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.swGenre.setOnRefreshListener {
            binding.swGenre.isRefreshing = false
            viewModel.getGenresAndMovie()
        }
    }

    private fun showShimmer(isShow: Boolean) {
        with(binding) {
            if (isShow) {
                shimmerGenre.startShimmer()
                shimmerGenre.visibility = View.VISIBLE
            } else {
                shimmerGenre.startShimmer()
                shimmerGenre.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}