package com.example.greedygamesassignment.datamodels

import com.google.gson.annotations.SerializedName

data class AlbumDataModel(
    @SerializedName("albums", alternate = ["topalbums"])
    val albums: Albums?
)

data class Albums(
    @SerializedName("album")
    val album: List<Album>?,
)

data class Album(
    @SerializedName("artist")
    val artist: Artist?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("name")
    val name: String?,
)

