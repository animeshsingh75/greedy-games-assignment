package com.example.greedygamesassignment.datamodels


import android.media.MediaParser.TrackData
import com.google.gson.annotations.SerializedName

data class TopTracksDataModel(
    @SerializedName("toptracks")
    val toptracks: Toptracks?
)

data class Toptracks(
    @SerializedName("track")
    val trackData: List<com.example.greedygamesassignment.datamodels.TrackData?>?
)

data class TrackData(
    @SerializedName("artist")
    val artist: Artist?,
    @SerializedName("image")
    val image: List<Image?>?,
    @SerializedName("name")
    val name: String?,
)