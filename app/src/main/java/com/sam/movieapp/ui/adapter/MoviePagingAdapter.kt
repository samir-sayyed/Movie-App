package com.sam.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sam.movieapp.R
import com.sam.movieapp.data.model.Movie
import com.sam.movieapp.databinding.MovieItemBinding
import com.sam.movieapp.util.IMAGE_PATH

class MoviePagingAdapter(private val onClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MoviePagingAdapter.ViewHolder>(
        CatsComparator
    ) {

    object CatsComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.movie = getItem(position)
        holder.view.imageMoviePoster.let {
            Glide.with(holder.itemView.context)
                .load(IMAGE_PATH+getItem(position)?.posterPath)
                .placeholder(R.drawable.movie_icon)
                .into(
                    it
                )
        }
        holder.view.root.setOnClickListener {
            getItem(position)?.let { it1 -> onClick(it1) }
        }
    }

    class ViewHolder(val view: MovieItemBinding) : RecyclerView.ViewHolder(view.root)

}