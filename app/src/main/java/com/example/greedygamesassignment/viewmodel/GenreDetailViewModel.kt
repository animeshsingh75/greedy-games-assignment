package com.example.greedygamesassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.greedygamesassignment.datamodels.AlbumDataModel
import com.example.greedygamesassignment.datamodels.ArtistDataModel
import com.example.greedygamesassignment.datamodels.GenreInfoDataModel
import com.example.greedygamesassignment.datamodels.TrackDataModel
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class GenreDetailViewModel(
    private val repo: Repo,
) : ViewModel() {

    private val _genreInfo = MutableLiveData<Resource<GenreInfoDataModel>>()
    val genreInfo: LiveData<Resource<GenreInfoDataModel>> = _genreInfo

    private val _albumList = MutableLiveData<Resource<AlbumDataModel>>()
    val albumList: LiveData<Resource<AlbumDataModel>> = _albumList

    private val _artistList = MutableLiveData<Resource<ArtistDataModel>>()
    val artistList: LiveData<Resource<ArtistDataModel>> = _artistList

    private val _trackList = MutableLiveData<Resource<TrackDataModel>>()
    val trackList: LiveData<Resource<TrackDataModel>> = _trackList

    fun getInitialData(genre: String) = viewModelScope.launch {

        _genreInfo.postValue(Resource.Loading())
        _albumList.postValue(Resource.Loading())
        val genreFlow = flowOf(repo.getGenreInfo(genre))
        val albumFlow = flowOf(repo.getAlbumList(genre))
        genreFlow.zip(albumFlow) { genreInfo, albumInfo ->
            _genreInfo.postValue(genreInfo)
            _albumList.postValue(albumInfo)
        }
            .catch { e ->
                e.message?.let {
                    _genreInfo.postValue(Resource.Error(it))
                    _albumList.postValue(Resource.Error(it))
                }

            }
            .collect() {}
    }

    fun getArtistList(genre: String) = viewModelScope.launch {
        _artistList.postValue(Resource.Loading())
        _artistList.postValue(repo.getArtistList(genre))
    }

    fun getTrackList(genre: String) = viewModelScope.launch {
        _trackList.postValue(Resource.Loading())
        _trackList.postValue(repo.getTrackList(genre))
    }

    class GenreDetailViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GenreDetailViewModel(repo) as T
        }
    }
}