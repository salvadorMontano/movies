package com.example.movies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SerieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(serie:Serie)

    @Query("SELECT * FROM series WHERE option_filter = :filter")
    fun getSeriesByOptionFilter(filter:String):List<Serie>

    @Query("SELECT * FROM series WHERE name LIKE '%' || :search || '%' ")
    fun getSeriesBySearchName(search:String):List<Serie>
}