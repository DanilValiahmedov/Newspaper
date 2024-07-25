package com.example.newspaper.data.datasource

import com.example.newspaper.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsAPI {
    @GET("{section}.json?api-key=PHMI6ymAlutApFz0VJLbhGZDU1yjhd4s")
    suspend fun getNewsBySection(@Path("section") section: String): Response<NewsResponse>
}