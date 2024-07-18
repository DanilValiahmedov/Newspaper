package com.example.newspaper

import retrofit2.http.GET
import retrofit2.http.Path

interface NewsAPI {
    @GET("{section}.json?api-key=PHMI6ymAlutApFz0VJLbhGZDU1yjhd4s")
    suspend fun getNewsBySection(@Path("section") section: String): News
}