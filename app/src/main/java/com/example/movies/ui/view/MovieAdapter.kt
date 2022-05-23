package com.example.movies.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.MovieModel

class MovieAdapter(
    private val movies:List<MovieModel>,
    private val movieClickListener:OnMovieClickListener
) : RecyclerView.Adapter<MovieViewHolder>() {


    interface OnMovieClickListener{
        fun onMovieClick(movie:MovieModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MovieViewHolder{
        var layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item:MovieModel = movies[position]

        if(item.poster_path!=null)
        {
            holder.bind(item.poster_path)
        }
        else
        {
            holder.bind("poster-error.jpeg")
        }

        holder.itemView.setOnClickListener{
            movieClickListener.onMovieClick(item)
        }
    }

}