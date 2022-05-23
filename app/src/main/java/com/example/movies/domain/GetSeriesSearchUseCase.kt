package com.example.movies.domain

import com.example.movies.data.MovieRepository
import com.example.movies.data.SerieRepository
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.SerieResponse

class GetSeriesSearchUseCase(val api_key:String,val page:Int,val query:String) {

    private val repository = SerieRepository()
    suspend operator fun invoke(): SerieResponse? = repository.getSeriesSearch(api_key,page,query)
}