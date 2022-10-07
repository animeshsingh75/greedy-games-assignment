package com.example.greedygamesassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.greedygamesassignment.datamodels.AlbumDataModel
import com.example.greedygamesassignment.datamodels.ArtistInfoDataModel
import com.example.greedygamesassignment.datamodels.TrackDataModel
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class ArtistInfoViewModel(
    private val repo: Repo,
) : ViewModel() {

    private val _artistInfo = MutableLiveData<Resource<ArtistInfoDataModel>>()
    val artistInfo: LiveData<Resource<ArtistInfoDataModel>> = _artistInfo

    private val _albumList = MutableLiveData<Resource<AlbumDataModel>>()
    val albumList: LiveData<Resource<AlbumDataModel>> = _albumList

    private val _trackList = MutableLiveData<Resource<TrackDataModel>>()
    val trackList: LiveData<Resource<TrackDataModel>> = _trackList

    fun getData(artist: String) = viewModelScope.launch {

        _artistInfo.postValue(Resource.Loading())
        _albumList.postValue(Resource.Loading())
        _trackList.postValue(Resource.Loading())
        val artistInfoFlow = flowOf(repo.getArtistInfo(artist))
        val albumFlow = flowOf(repo.getAlbumByArtist(artist))
        val trackFlow = flowOf(repo.getTrackByArtist(artist))

        artistInfoFlow.zip(albumFlow) { artistInfo, albumInfo ->
            Pair(artistInfo, albumInfo)
        }
            .zip(trackFlow) { pair, trackInfo ->
                _artistInfo.postValue(pair.first)
                _albumList.postValue(pair.second)
                _trackList.postValue(trackInfo)
            }.catch { e ->
                e.message?.let {
                    _artistInfo.postValue(Resource.Error(it))
                    _albumList.postValue(Resource.Error(it))
                    _trackList.postValue(Resource.Error(it))
                }
            }
            .flowOn(Dispatchers.IO)
            .collect() {}

    }

    class ArtistInfoViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArtistInfoViewModel(repo) as T
        }
    }
}