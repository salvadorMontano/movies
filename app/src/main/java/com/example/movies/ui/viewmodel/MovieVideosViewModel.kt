package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.MovieVideoResponse
import com.example.movies.domain.GetMovieVideosUseCase
import com.example.movies.domain.GetMoviesPopularUseCase
import kotlinx.coroutines.launch


class MovieVideosViewModel: ViewModel() {

    val movieVideoResponse = MutableLiveData<MovieVideoResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(id:String,api_key:String){
        var getMovieVideosUseCase = GetMovieVideosUseCase(id,api_key)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMovieVideosUseCase()
            if(result != null){
                movieVideoResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

}