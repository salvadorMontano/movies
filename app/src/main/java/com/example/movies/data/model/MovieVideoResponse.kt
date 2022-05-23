package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("results") var results:List<MovieVideoModel>,
)
