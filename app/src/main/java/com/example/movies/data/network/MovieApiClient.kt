package com.example.movies.data.network

import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

    @GET("movie/popular")
    suspend fun getMoviesPopular(@Query("api_key") api_key: String?,@Query("page") page:Int?): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(@Query("api_key") api_key: String?,@Query("page") page:Int?): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getMoviesSearch(@Query("api_key") api_key: String?,@Query("page") page:Int?,@Query("query") query:String?): Response<MovieResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(@Path("id") id: String?, @Query("api_key") api_key: String?): Response<MovieVideoResponse>

}