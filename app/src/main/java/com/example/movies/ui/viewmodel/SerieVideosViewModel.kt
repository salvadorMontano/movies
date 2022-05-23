package com.example.movies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieVideoResponse
import com.example.movies.data.model.SerieVideoResponse
import com.example.movies.domain.GetMovieVideosUseCase
import com.example.movies.domain.GetSerieVideosUseCase
import kotlinx.coroutines.launch

class SerieVideosViewModel: ViewModel() {

    val serieVideoResponse = MutableLiveData<SerieVideoResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(id:String,api_key:String){
        var getSerieVideosUseCase = GetSerieVideosUseCase(id,api_key)
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getSerieVideosUseCase()
            if(result != null){
                serieVideoResponse.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

}