package com.example.omdbmoviebrowser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.omdbmoviebrowser.R
import com.example.omdbmoviebrowser.databinding.FragmentMovieDetailBinding
import com.example.omdbmoviebrowser.domain.model.MovieDetail
import com.example.omdbmoviebrowser.domain.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    private var currentMovie: MovieDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMovieDetails(args.imdbId)
        observeMovieDetails()
        setupFavoriteButton()
    }

    private fun observeMovieDetails() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    resource.data?.let { displayMovieDetails(it) }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayMovieDetails(movie: MovieDetail) {
        currentMovie = movie

        binding.apply {
            tvTitle.text = movie.title
            tvYear.text = movie.year
            tvPlot.text = movie.plot

            movie.runtime?.let { tvRuntime.text = "Тривалість: $it" }
            movie.genre?.let { tvGenre.text = "Жанр: $it" }
            movie.director?.let { tvDirector.text = "Режисер: $it" }
            movie.actors?.let { tvActors.text = "Актори: $it" }
            movie.imdbRating?.let { tvRating.text = "IMDB: $it" }

            Glide.with(requireContext())
                .load(movie.poster)
                .placeholder(R.drawable.ic_movie_placeholder)
                .error(R.drawable.ic_movie_placeholder)
                .into(ivPoster)

            updateFavoriteButton(movie.isFavorite)
        }
    }

    private fun setupFavoriteButton() {
        binding.fabFavorite.setOnClickListener {
            currentMovie?.let { movie ->
                viewModel.toggleFavorite(movie)
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding.fabFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}