package com.example.movies.domain

import com.example.movies.data.MovieRepository
import com.example.movies.data.model.MovieResponse

class GetMoviesSearchUseCase(val api_key:String,val page:Int,val query:String ) {

    private val repository = MovieRepository()
    suspend operator fun invoke(): MovieResponse? = repository.getMoviesSearch(api_key,page,query)
}