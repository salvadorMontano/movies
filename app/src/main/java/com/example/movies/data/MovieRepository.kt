package com.example.movies.data

import com.example.movies.data.model.MovieProvider
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoProvider
import com.example.movies.data.model.MovieVideoResponse
import com.example.movies.data.network.MovieService

class MovieRepository {

    private val api = MovieService()

    suspend fun getMoviesPopular(api_key:String,page:Int):MovieResponse{
        val response = api.getMoviesPopular(api_key,page)
        MovieProvider.movies = response
        return  response
    }

    suspend fun getMoviesTopRated(api_key:String,page:Int):MovieResponse{
        val response = api.getMoviesTopRated(api_key,page)
        MovieProvider.movies = response
        return  response
    }

    suspend fun getMoviesSearch(api_key:String,page:Int,query:String):MovieResponse{
        val response = api.getMoviesSearch(api_key,page,query)
        MovieProvider.movies = response
        return  response
    }

    suspend fun getMovieVideos(id:String,api_key:String): MovieVideoResponse {
        val response = api.getMovieVideos(id,api_key)
        MovieVideoProvider.videos = response
        return  response
    }




}