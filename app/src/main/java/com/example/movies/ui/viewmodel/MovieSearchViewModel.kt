package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.domain.GetMoviesPopularUseCase
import com.example.movies.domain.GetMoviesSearchUseCase
import kotlinx.coroutines.launch

class MovieSearchViewModel: ViewModel() {

    val movieResponse = MutableLiveData<MovieResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(api_key:String,page:Int,query:String){
        var getMoviesSearchUseCase = GetMoviesSearchUseCase(api_key,page,query)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMoviesSearchUseCase()
            if(result != null){
                movieResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

}