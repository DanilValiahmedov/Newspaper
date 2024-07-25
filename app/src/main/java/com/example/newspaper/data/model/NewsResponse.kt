package com.example.newspaper.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("num_results") val numResults: Int,
    val results: List<ResultResponse>,
)

@Serializable
data class ResultResponse(
    val title: String,
    val abstract: String,
    val url: String,
    val byline: String,
    val multimedia: List<MultimediaResponse>?,
)

@Serializable
data class MultimediaResponse(
    val url: String,
)
