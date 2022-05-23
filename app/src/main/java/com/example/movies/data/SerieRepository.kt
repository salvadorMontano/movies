package com.example.movies.data

import com.example.movies.data.model.*
import com.example.movies.data.network.MovieService
import com.example.movies.data.network.SerieService

class SerieRepository {

    private val api = SerieService()

    suspend fun getSeriesPopular(api_key:String,page:Int): SerieResponse {
        val response = api.getSeriesPopular(api_key,page)
        SerieProvider.series = response
        return  response
    }

    suspend fun getSeriesTopRated(api_key:String,page:Int): SerieResponse {
        val response = api.getSeriesTopRated(api_key,page)
        SerieProvider.series = response
        return  response
    }

    suspend fun getSeriesSearch(api_key:String,page:Int,query:String): SerieResponse {
        val response = api.getSeriesSearch(api_key,page,query)
        SerieProvider.series = response
        return  response
    }

    suspend fun getSerieVideos(id:String,api_key:String): SerieVideoResponse {
        val response = api.getSerieVideos(id,api_key)
        SerieVideoProvider.series = response
        return  response
    }

}