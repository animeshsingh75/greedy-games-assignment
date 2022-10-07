package com.example.greedygamesassignment.datamodels


import com.google.gson.annotations.SerializedName

data class GenreInfoDataModel(
    @SerializedName("tag")
    val tag: TagInfo?
)

data class TagInfo(
    @SerializedName("name")
    val name: String?,
    @SerializedName("wiki")
    val wiki: Wiki?
)

