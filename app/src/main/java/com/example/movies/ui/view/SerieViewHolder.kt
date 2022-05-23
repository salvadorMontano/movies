package com.example.movies.ui.view

import android.R
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies.databinding.ItemSerieBinding.*


class SerieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = bind(view)
    fun bind(poster_path:String){

        if(poster_path != "poster-error.jpeg")
        {
            Glide.with(binding.posterItem.context)
                .load("https://image.tmdb.org/t/p/original/"+poster_path).diskCacheStrategy(
                    DiskCacheStrategy.ALL)
                .into(binding.posterItem)
        }

    }

}