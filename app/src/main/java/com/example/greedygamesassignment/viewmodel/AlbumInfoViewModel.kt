package com.example.greedygamesassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.greedygamesassignment.datamodels.AlbumDataModel
import com.example.greedygamesassignment.datamodels.AlbumInfoDataModel
import com.example.greedygamesassignment.datamodels.ArtistDataModel
import com.example.greedygamesassignment.datamodels.GenreInfoDataModel
import com.example.greedygamesassignment.datamodels.TrackDataModel
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class AlbumInfoViewModel(
    private val repo: Repo,
) : ViewModel() {

    private val _albumInfo = MutableLiveData<Resource<AlbumInfoDataModel>>()
    val albumInfo: LiveData<Resource<AlbumInfoDataModel>> = _albumInfo


    fun getAlbumInfo(artist: String,album:String) = viewModelScope.launch {
        _albumInfo.postValue(Resource.Loading())
        _albumInfo.postValue(repo.getAlbumInfo(artist, album))
    }

    class AlbumInfoViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AlbumInfoViewModel(repo) as T
        }
    }
}