package com.example.greedygamesassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.greedygamesassignment.datamodels.GenreDataModel
import com.example.greedygamesassignment.datamodels.GenreInfoDataModel
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repo: Repo,
) : ViewModel() {

    private val _genreList = MutableLiveData<Resource<GenreDataModel>>()
    val genreList: LiveData<Resource<GenreDataModel>> = _genreList



    fun getGenreList() = viewModelScope.launch {
        _genreList.postValue(Resource.Loading())
        _genreList.postValue(repo.getGenreList())
    }


    class MainActivityViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(repo) as T
        }
    }
}