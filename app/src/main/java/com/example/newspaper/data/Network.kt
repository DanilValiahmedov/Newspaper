package com.example.newspaper.data

import com.example.newspaper.data.datasource.NewsAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Network {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsApi = retrofit.create(NewsAPI::class.java)

}