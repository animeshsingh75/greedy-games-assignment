package com.example.greedygamesassignment.datamodels

import com.google.gson.annotations.SerializedName

data class AlbumInfoDataModel(
    @SerializedName("album") val albumInfo: AlbumInfo?
)

data class AlbumInfo(
    @SerializedName("artist") val artist: String?,
    @SerializedName("image") val image: List<Image?>?,
    @SerializedName("name") val name: String?,
    @SerializedName("tags") val tags: Tags?,
    @SerializedName("wiki") val wiki: Wiki?
)

