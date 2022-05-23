package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.SerieResponse
import com.example.movies.domain.GetMoviesTopRatedUseCase
import com.example.movies.domain.GetSeriesTopRatedUseCase
import kotlinx.coroutines.launch

class SerieTopRatedViewModel: ViewModel() {

    val serieResponse = MutableLiveData<SerieResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(api_key:String,page:Int){
        var getSeriesTopRatedUseCase = GetSeriesTopRatedUseCase(api_key,page)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getSeriesTopRatedUseCase()
            if(result != null){
                serieResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

}