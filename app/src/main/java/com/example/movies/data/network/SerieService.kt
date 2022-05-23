package com.example.movies.data.network

import com.example.movies.core.RetrofitHelper
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoResponse
import com.example.movies.data.model.SerieResponse
import com.example.movies.data.model.SerieVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SerieService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSeriesPopular(api_key:String,page:Int): SerieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SerieApiClient::class.java).getSeriesPopular("$api_key",page)
            response.body() ?: SerieResponse(0, emptyList(),0,0)
        }
    }

    suspend fun getSeriesTopRated(api_key:String,page:Int): SerieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SerieApiClient::class.java).getSeriesTopRated("$api_key",page)
            response.body() ?: SerieResponse(0, emptyList(),0,0)
        }
    }

    suspend fun getSeriesSearch(api_key:String,page:Int,query:String): SerieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SerieApiClient::class.java).getSeriesSearch("$api_key",page,"$query")
            response.body() ?: SerieResponse(0, emptyList(),0,0)
        }
    }

    suspend fun getSerieVideos(id:String,api_key:String): SerieVideoResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(SerieApiClient::class.java).getSerieVideos("$id","$api_key")
            response.body() ?: SerieVideoResponse(emptyList())
        }
    }


}