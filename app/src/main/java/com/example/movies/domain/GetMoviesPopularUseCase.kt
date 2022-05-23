package com.example.movies.domain

import com.example.movies.data.MovieRepository
import com.example.movies.data.model.MovieResponse

class GetMoviesPopularUseCase(val api_key:String,val page:Int) {

    private val repository = MovieRepository()
    suspend operator fun invoke():MovieResponse? = repository.getMoviesPopular(api_key,page)

}