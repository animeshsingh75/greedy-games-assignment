package com.example.greedygamesassignment.datamodels

import com.google.gson.annotations.SerializedName

data class ArtistInfoDataModel(
    @SerializedName("artist")
    val artistInfo: ArtistInfo?
)

data class ArtistInfo(
    @SerializedName("bio")
    val bio: Bio?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("stats")
    val stats: Stats?,
    @SerializedName("tags")
    val tags: Tags?,
)

data class Bio(
    val summary: String?
)

data class Stats(
    @SerializedName("listeners")
    val listeners: Int?,
    @SerializedName("playcount")
    val playcount: Int?
)