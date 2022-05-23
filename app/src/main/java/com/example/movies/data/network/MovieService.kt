package com.example.movies.data.network

import com.example.movies.core.RetrofitHelper
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getMoviesPopular(api_key:String,page:Int):MovieResponse{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getMoviesPopular("$api_key",page)
            response.body() ?: MovieResponse(0, emptyList(),0,0)
        }
    }

    suspend fun getMoviesTopRated(api_key:String,page:Int):MovieResponse{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getMoviesTopRated("$api_key",page)
            response.body() ?: MovieResponse(0, emptyList(),0,0)
        }
    }

    suspend fun getMoviesSearch(api_key:String,page:Int,query:String):MovieResponse{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getMoviesSearch("$api_key",page,"$query")
            response.body() ?: MovieResponse(0, emptyList(),0,0)
        }
    }

    suspend fun getMovieVideos(id:String,api_key:String): MovieVideoResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getMovieVideos("$id","$api_key")
            response.body() ?: MovieVideoResponse(emptyList())
        }
    }





}