package com.example.greedygamesassignment.datamodels

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    val text: String
)

data class Artist(
    @SerializedName("name")
    val name: String?,
)

data class Wiki(
    @SerializedName("summary")
    val summary: String?
)

data class Tags(
    @SerializedName("tag") val tag: List<Tag>?
)

data class Tag(
    @SerializedName("name")
    val name: String?,
)
