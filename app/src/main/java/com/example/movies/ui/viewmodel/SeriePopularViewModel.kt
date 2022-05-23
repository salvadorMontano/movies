package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.SerieResponse
import com.example.movies.domain.GetMoviesPopularUseCase
import com.example.movies.domain.GetSeriesPopularUseCase
import kotlinx.coroutines.launch

class SeriePopularViewModel: ViewModel() {

    val serieResponse = MutableLiveData<SerieResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(api_key:String,page:Int){
        var getSeriesPopularUseCase = GetSeriesPopularUseCase(api_key,page)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getSeriesPopularUseCase()
            if(result != null){
                serieResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }


}