package com.example.greedygamesassignment.datamodels


import com.google.gson.annotations.SerializedName

data class ArtistDataModel(
    @SerializedName("topartists")
    val topArtists: TopArtists?
)

data class TopArtists(
    @SerializedName("artist")
    val artist: List<ArtistData>?,
)

data class ArtistData(
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("name")
    val name: String?,
)