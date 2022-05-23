package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.domain.GetMoviesPopularUseCase
import com.example.movies.domain.GetMoviesTopRatedUseCase
import kotlinx.coroutines.launch

class MovieTopRatedViewModel: ViewModel() {

    val movieResponse = MutableLiveData<MovieResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(api_key:String,page:Int){
        var getMoviesTopRatedUseCase = GetMoviesTopRatedUseCase(api_key,page)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMoviesTopRatedUseCase()
            if(result != null){
                movieResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }
}