package com.example.newspaper.data.datasource

import com.example.newspaper.data.Network
import com.example.newspaper.data.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object NewsDataSource {
    suspend fun getNewsList(section: String): Result<News> = request {
        Network.newsApi.getNewsBySection(section)
    }

    private suspend fun <T>request(requestApi: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = requestApi()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body)
                    } else {
                        Result.failure(Exception())
                    }
                } else {
                    Result.failure(Exception())
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }
}