package com.example.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class,Serie::class], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {

 abstract  val movieDao:MovieDao
 abstract  val serieDao:SerieDao
 companion object{
     const val DATABASE_NAME = "db-movie"
 }

}