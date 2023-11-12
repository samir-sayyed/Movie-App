package com.sam.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.sam.movieapp.R
import com.sam.movieapp.data.model.MovieDetailsResponse
import com.sam.movieapp.databinding.FragmentMovieDetailsBinding
import com.sam.movieapp.util.IMAGE_PATH
import com.sam.movieapp.util.SOMETHING_WENT_WRONG
import com.sam.movieapp.util.StorageUtil
import com.sam.movieapp.util.dismissLoadingDialog
import com.sam.movieapp.util.showLoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var viewBinding: FragmentMovieDetailsBinding? = null
    private val mMovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.movie.id?.let { mMovieViewModel.getMovieDetails(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingDialog()
        initUi()
        observeData()
    }

    private fun observeData() {
        mMovieViewModel.movieDetails.observe(viewLifecycleOwner) { movieDetailsResponse ->
            dismissLoadingDialog()
            showDetails(movieDetailsResponse)
        }

        mMovieViewModel.error.observe(viewLifecycleOwner) {
            dismissLoadingDialog()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            val details = MovieDetailsResponse().apply {
                title = args.movie.title
                overview = args.movie.overview
                poster_path = args.movie.posterPath
                vote_average = args.movie.voteAverage
                release_date = args.movie.releaseDate
                original_language = args.movie.originalLanguage
            }
            showDetails(details)
        }
    }

    private fun showDetails(movieDetailsResponse: MovieDetailsResponse?) {
        viewBinding?.movieDetails = movieDetailsResponse
        Glide.with(requireContext())
            .load(IMAGE_PATH + movieDetailsResponse?.poster_path)
            .placeholder(R.drawable.movie_icon)
            .into(viewBinding?.imagePoster!!)

        viewBinding?.chipGroup?.removeAllViews()

        movieDetailsResponse?.genres?.forEach {
            val chip = Chip(requireContext())
            chip.text = it?.name
            viewBinding?.chipGroup?.addView(chip)
        }
    }

    private fun initUi() {
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.movie_details)
        val isDarkTheme = StorageUtil.getInstance(requireContext()).isDarkTheme()
        if (isDarkTheme) {
            viewBinding?.textVote?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.dark_star_icon, 0, 0, 0
            )
            viewBinding?.textLanguage?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.dark_language_icon, 0, 0, 0
            )
            viewBinding?.textReleaseDate?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.dark_timer_icon, 0, 0, 0
            )
        } else {
            viewBinding?.textVote?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.star_icon, 0, 0, 0
            )
            viewBinding?.textLanguage?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.language_icon, 0, 0, 0
            )
            viewBinding?.textReleaseDate?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.timer_icon, 0, 0, 0
            )
        }
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }
}