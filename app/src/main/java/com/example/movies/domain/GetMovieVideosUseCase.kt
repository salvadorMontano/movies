package com.example.movies.domain

import com.example.movies.data.MovieRepository
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoResponse

class GetMovieVideosUseCase(val id:String,val api_key:String) {

    private val repository = MovieRepository()
    suspend operator fun invoke(): MovieVideoResponse? = repository.getMovieVideos(id,api_key)
}