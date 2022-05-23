package com.example.movies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insert(movie:Movie)

   @Query("SELECT * FROM movies WHERE option_filter = :filter")
   fun getMoviesByOptionFilter(filter:String):List<Movie>

   @Query("SELECT * FROM movies WHERE title LIKE '%' || :search || '%' ")
   fun getMoviesBySearchTitle(search:String):List<Movie>


}