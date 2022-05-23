package com.example.movies.data.network

import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoResponse
import com.example.movies.data.model.SerieResponse
import com.example.movies.data.model.SerieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SerieApiClient {

    @GET("tv/popular")
    suspend fun getSeriesPopular(@Query("api_key") api_key: String?, @Query("page") page:Int?): Response<SerieResponse>

    @GET("tv/top_rated")
    suspend fun getSeriesTopRated(@Query("api_key") api_key: String?, @Query("page") page:Int?): Response<SerieResponse>

    @GET("search/tv")
    suspend fun getSeriesSearch(@Query("api_key") api_key: String?, @Query("page") page:Int?, @Query("query") query:String?): Response<SerieResponse>

    @GET("tv/{id}/videos")
    suspend fun getSerieVideos(@Path("id") id: String?, @Query("api_key") api_key: String?): Response<SerieVideoResponse>

}