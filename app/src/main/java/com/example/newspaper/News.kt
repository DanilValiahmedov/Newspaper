package com.example.newspaper

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val num_results: Int,
    val results: List<Result>,
)

@Serializable
data class Result(
    val title: String,
    val abstract: String,
    val url: String,
    val byline: String,
    val multimedia: List<Multimedia>,
)

@Serializable
data class Multimedia(
    val url: String,
)
