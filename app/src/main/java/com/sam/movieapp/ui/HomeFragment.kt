package com.sam.movieapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.sam.movieapp.R
import com.sam.movieapp.databinding.FragmentHomeBinding
import com.sam.movieapp.ui.adapter.MoviePagingAdapter
import com.sam.movieapp.util.LoaderAdapter
import com.sam.movieapp.util.StorageUtil
import com.sam.movieapp.util.dismissLoadingDialog
import com.sam.movieapp.util.showLoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var viewBinding: FragmentHomeBinding? = null

    private lateinit var mMoviePagingDataAdapter: MoviePagingAdapter

    private val mMovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingDialog()
        if (StorageUtil.getInstance(requireContext()).isDarkTheme())
            viewBinding?.themeButton?.setImageResource(R.drawable.baseline_wb_sunny_24)
        else
            viewBinding?.themeButton?.setImageResource(R.drawable.baseline_nightlight_round_24)
        mMovieViewModel.getMovies()
        initAdapter()
        (requireActivity() as MainActivity).supportActionBar?.title = "Movies"
        observeData()
        initListeners()
    }

    private fun initAdapter() {
        mMoviePagingDataAdapter = MoviePagingAdapter(
            onClick = {
                it.let { movie ->
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                            movie = movie
                        )
                    )
                }
            }
        )
        val layoutManager = GridLayoutManager(activity, 3)
        viewBinding?.moviesRecyclerview?.layoutManager = layoutManager
        viewBinding?.moviesRecyclerview?.adapter = mMoviePagingDataAdapter.withLoadStateFooter(
            footer = LoaderAdapter()
        )
        mMoviePagingDataAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                Log.i(TAG, "Loading")
            } else {
                dismissLoadingDialog()
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Log.e(TAG, it.error.message.toString())
                    Toast.makeText(
                        requireContext(),
                        it.error.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observeData() {
        mMovieViewModel.movieList.observe(viewLifecycleOwner) {
            mMoviePagingDataAdapter.submitData(lifecycle, it)
        }
    }

    private fun initListeners() {
        viewBinding?.themeButton?.setOnClickListener {
            if (StorageUtil.getInstance(requireContext()).isDarkTheme())
                viewBinding?.themeButton?.setImageResource(R.drawable.baseline_nightlight_round_24)
            else
                viewBinding?.themeButton?.setImageResource(R.drawable.baseline_wb_sunny_24)
            (requireActivity() as MainActivity).switchTheme()
        }

    }
}
