package com.example.greedygamesassignment.datamodels

import com.google.gson.annotations.SerializedName

data class GenreDataModel(
    @SerializedName("toptags")
    val topTags: TopTags?
)

data class TopTags(
    @SerializedName("tag")
    val tag: List<Tag>?
)
