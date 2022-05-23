package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class SerieResponse(
    @SerializedName("page") var page:Int,
    @SerializedName("results") var results:List<SerieModel>,
    @SerializedName("total_pages") var total_pages:Int,
    @SerializedName("total_results") var total_results:Int
)
