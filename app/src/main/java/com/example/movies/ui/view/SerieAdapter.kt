package com.example.movies.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.SerieModel

class SerieAdapter(
    private val series:List<SerieModel>,
    private val serieClickListener:OnSerieClickListener
): RecyclerView.Adapter<SerieViewHolder>() {

    interface OnSerieClickListener{
        fun onSerieClick(serie:SerieModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SerieViewHolder{
        var layoutInflater = LayoutInflater.from(parent.context)
        return SerieViewHolder(layoutInflater.inflate(R.layout.item_serie,parent,false))
    }

    override fun getItemCount(): Int = series.size

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val item:SerieModel = series[position]

        if(item.poster_path!=null){
            holder.bind(item.poster_path)
        }else{
            holder.bind("poster-error.jpeg")
        }

        holder.itemView.setOnClickListener{
            serieClickListener.onSerieClick(item)
        }
    }


}