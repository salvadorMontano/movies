package com.example.movies.data.database

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "movies",indices = [Index(value = ["id"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var idu:Long=0,
    @NonNull
    var id:Int=0,
    @Nullable
    var title:String,
    var overview:String,
    var poster_path:String,
    var release_date:String,
    var vote_average:Double,
    var vote_count:Int,
    var option_filter:String

)
