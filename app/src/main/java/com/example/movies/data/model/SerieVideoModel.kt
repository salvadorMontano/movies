package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class SerieVideoModel(
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
    @SerializedName("key") var key:String,
    @SerializedName("published_at") var published_at:String,
    @SerializedName("site") var site:String,
    @SerializedName("type") var type:String
)
