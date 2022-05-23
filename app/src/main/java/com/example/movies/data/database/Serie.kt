package com.example.movies.data.database

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "series",indices = [Index(value = ["id"], unique = true)])
data class Serie(
    @PrimaryKey(autoGenerate = true)
    var idu:Long=0,
    @NonNull
    var id:Int=0,
    @Nullable
    var name:String,
    var overview:String,
    var poster_path:String,
    var first_air_date:String,
    var vote_average:Double,
    var vote_count:Int,
    var option_filter:String
)
