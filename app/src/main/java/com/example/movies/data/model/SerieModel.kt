package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class SerieModel(
    @SerializedName("id") var id:Int,
    @SerializedName("overview") var overview:String,
    @SerializedName("poster_path") var poster_path:String,
    @SerializedName("first_air_date") var first_air_date:String,
    @SerializedName("name") var name:String,
    @SerializedName("vote_average") var vote_average:Double,
    @SerializedName("vote_count") var vote_count:Int
)
