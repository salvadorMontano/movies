package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.domain.GetMoviesPopularUseCase
import kotlinx.coroutines.launch

class MoviePopularViewModel: ViewModel() {

    val movieResponse = MutableLiveData<MovieResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(api_key:String,page:Int){
        var getMoviesPopularUseCase = GetMoviesPopularUseCase(api_key,page)
        viewModelScope.launch {
          isLoading.postValue(true)
          val result = getMoviesPopularUseCase()
          if(result != null){
              movieResponse.postValue(result!!)
              isLoading.postValue(false)
          }
        }
    }

}