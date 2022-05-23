package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") var page:Int,
    @SerializedName("results") var results:List<MovieModel>,
    @SerializedName("total_pages") var total_pages:Int,
    @SerializedName("total_results") var total_results:Int


)
