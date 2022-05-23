package com.example.movies.domain

import com.example.movies.data.MovieRepository
import com.example.movies.data.SerieRepository
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.SerieResponse

class GetSeriesTopRatedUseCase(val api_key:String,val page:Int) {

    private val repository = SerieRepository()
    suspend operator fun invoke(): SerieResponse? = repository.getSeriesTopRated(api_key,page)

}