package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.SerieResponse
import com.example.movies.domain.GetMoviesSearchUseCase
import com.example.movies.domain.GetSeriesSearchUseCase
import kotlinx.coroutines.launch

class SerieSearchViewModel: ViewModel() {

    val serieResponse = MutableLiveData<SerieResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(api_key:String,page:Int,query:String){
        var getSeriesSearchUseCase = GetSeriesSearchUseCase(api_key,page,query)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getSeriesSearchUseCase()
            if(result != null){
                serieResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

}