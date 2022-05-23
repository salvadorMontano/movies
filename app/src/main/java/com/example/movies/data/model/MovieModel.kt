package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id") var id:Int,
    @SerializedName("overview") var overview:String,
    @SerializedName("poster_path") var poster_path:String,
    @SerializedName("release_date") var release_date:String,
    @SerializedName("title") var title:String,
    @SerializedName("vote_average") var vote_average:Double,
    @SerializedName("vote_count") var vote_count:Int
    )
