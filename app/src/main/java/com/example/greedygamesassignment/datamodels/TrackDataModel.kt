package com.example.greedygamesassignment.datamodels


import com.google.gson.annotations.SerializedName

data class TrackDataModel(
    @SerializedName("tracks", alternate = ["toptracks"])
    val tracks: Tracks
)

data class Tracks(
    @SerializedName("track")
    val track: List<Track>?
)

data class Track(
    @SerializedName("artist")
    val artist: Artist?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("name")
    val name: String?,

)