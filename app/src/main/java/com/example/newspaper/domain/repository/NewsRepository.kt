package com.example.newspaper.domain.repository

import com.example.newspaper.domain.model.News

interface NewsRepository {
    suspend fun getListNews(section: String): Result<List<News>>
}