package com.example.movies.domain

import com.example.movies.data.MovieRepository
import com.example.movies.data.SerieRepository
import com.example.movies.data.model.MovieVideoResponse
import com.example.movies.data.model.SerieVideoResponse

class GetSerieVideosUseCase(val id:String,val api_key:String) {

    private val repository = SerieRepository()
    suspend operator fun invoke(): SerieVideoResponse? = repository.getSerieVideos(id,api_key)

}