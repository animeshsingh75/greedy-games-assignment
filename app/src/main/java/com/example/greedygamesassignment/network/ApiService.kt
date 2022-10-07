package com.example.greedygamesassignment.network

import com.example.greedygamesassignment.Constants.ALBUM
import com.example.greedygamesassignment.Constants.API_KEY
import com.example.greedygamesassignment.Constants.ARTIST
import com.example.greedygamesassignment.Constants.FORMAT
import com.example.greedygamesassignment.Constants.METHOD
import com.example.greedygamesassignment.Constants.TAG
import com.example.greedygamesassignment.datamodels.AlbumDataModel
import com.example.greedygamesassignment.datamodels.AlbumInfoDataModel
import com.example.greedygamesassignment.datamodels.ArtistDataModel
import com.example.greedygamesassignment.datamodels.ArtistInfoDataModel
import com.example.greedygamesassignment.datamodels.GenreDataModel
import com.example.greedygamesassignment.datamodels.GenreInfoDataModel
import com.example.greedygamesassignment.datamodels.TrackDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getGenreList(
        @Query(METHOD) methodName: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<GenreDataModel>

    @GET(".")
    suspend fun getAlbumList(
        @Query(METHOD) methodName: String,
        @Query(TAG) tag: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<AlbumDataModel>

    @GET(".")
    suspend fun getArtistList(
        @Query(METHOD) methodName: String,
        @Query(TAG) tag: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<ArtistDataModel>

    @GET(".")
    suspend fun getTrackList(
        @Query(METHOD) methodName: String,
        @Query(TAG) tag: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<TrackDataModel>

    @GET(".")
    suspend fun getGenreInfo(
        @Query(METHOD) methodName: String,
        @Query(TAG) tag: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<GenreInfoDataModel>

    @GET(".")
    suspend fun getAlbumInfo(
        @Query(METHOD) methodName: String,
        @Query(API_KEY) apiKey: String,
        @Query(ARTIST) artist: String,
        @Query(ALBUM) album: String,
        @Query(FORMAT) format: String
    ): Response<AlbumInfoDataModel>

    @GET(".")
    suspend fun getArtistInfo(
        @Query(METHOD) methodName: String,
        @Query(API_KEY) apiKey: String,
        @Query(ARTIST) artist: String,
        @Query(FORMAT) format: String
    ): Response<ArtistInfoDataModel>

    @GET(".")
    suspend fun getTopAlbumByArtist(
        @Query(METHOD) methodName: String,
        @Query(ARTIST) artist: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<AlbumDataModel>

    @GET(".")
    suspend fun getTopTrackByArtist(
        @Query(METHOD) methodName: String,
        @Query(ARTIST) artist: String,
        @Query(API_KEY) apiKey: String,
        @Query(FORMAT) format: String
    ): Response<TrackDataModel>
}