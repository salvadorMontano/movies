package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class SerieVideoResponse(
    @SerializedName("results") var results:List<SerieVideoModel>,
)
