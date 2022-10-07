package com.example.greedygamesassignment.repo

import com.example.greedygamesassignment.Constants.ALBUM_INFO
import com.example.greedygamesassignment.Constants.API_KEY_VALUE
import com.example.greedygamesassignment.Constants.ARTIST_INFO
import com.example.greedygamesassignment.Constants.ARTIST_TOP_ALBUM
import com.example.greedygamesassignment.Constants.ARTIST_TOP_TRACKS
import com.example.greedygamesassignment.Constants.GENRE_INFO
import com.example.greedygamesassignment.Constants.JSON
import com.example.greedygamesassignment.Constants.TOP_ALBUM
import com.example.greedygamesassignment.Constants.TOP_ARTIST
import com.example.greedygamesassignment.Constants.TOP_GENRE_TAG
import com.example.greedygamesassignment.Constants.TOP_TRACKS
import com.example.greedygamesassignment.datamodels.AlbumDataModel
import com.example.greedygamesassignment.datamodels.AlbumInfoDataModel
import com.example.greedygamesassignment.datamodels.ArtistDataModel
import com.example.greedygamesassignment.datamodels.ArtistInfoDataModel
import com.example.greedygamesassignment.datamodels.GenreDataModel
import com.example.greedygamesassignment.datamodels.GenreInfoDataModel
import com.example.greedygamesassignment.datamodels.TopTracksDataModel
import com.example.greedygamesassignment.datamodels.TrackDataModel
import com.example.greedygamesassignment.network.ApiService
import com.example.greedygamesassignment.network.Resource

class Repo(private val apiService: ApiService) : BaseRepo() {

    suspend fun getGenreList(): Resource<GenreDataModel> {
        return safeApiCall { apiService.getGenreList(TOP_GENRE_TAG, API_KEY_VALUE, JSON) }
    }

    suspend fun getGenreInfo(tag: String): Resource<GenreInfoDataModel> {
        return safeApiCall { apiService.getGenreInfo(GENRE_INFO, tag, API_KEY_VALUE, JSON) }
    }

    suspend fun getAlbumList(tag: String): Resource<AlbumDataModel> {
        return safeApiCall { apiService.getAlbumList(TOP_ALBUM, tag, API_KEY_VALUE, JSON) }
    }

    suspend fun getAlbumInfo(artist: String, album: String): Resource<AlbumInfoDataModel> {
        return safeApiCall {
            apiService.getAlbumInfo(
                ALBUM_INFO,
                API_KEY_VALUE,
                artist,
                album,
                JSON
            )
        }
    }

    suspend fun getArtistList(tag: String): Resource<ArtistDataModel> {
        return safeApiCall { apiService.getArtistList(TOP_ARTIST, tag, API_KEY_VALUE, JSON) }
    }

    suspend fun getArtistInfo(artist: String): Resource<ArtistInfoDataModel> {
        return safeApiCall {
            apiService.getArtistInfo(
                ARTIST_INFO,
                API_KEY_VALUE,
                artist,
                JSON
            )
        }
    }

    suspend fun getAlbumByArtist(artist: String): Resource<AlbumDataModel> {
        return safeApiCall {
            apiService.getTopAlbumByArtist(
                ARTIST_TOP_ALBUM,
                artist,
                API_KEY_VALUE,
                JSON
            )
        }
    }

    suspend fun getTrackByArtist(artist: String): Resource<TrackDataModel> {
        return safeApiCall {
            apiService.getTopTrackByArtist(
                ARTIST_TOP_TRACKS,
                artist,
                API_KEY_VALUE,
                JSON
            )
        }
    }

    suspend fun getTrackList(tag: String): Resource<TrackDataModel> {
        return safeApiCall { apiService.getTrackList(TOP_TRACKS, tag, API_KEY_VALUE, JSON) }
    }
}