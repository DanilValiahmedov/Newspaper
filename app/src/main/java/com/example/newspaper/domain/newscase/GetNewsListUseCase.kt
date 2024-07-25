package com.example.newspaper.domain.newscase

import com.example.newspaper.domain.model.News
import com.example.newspaper.domain.repository.NewsRepository

class GetNewsListUseCase(private val newsRepository: NewsRepository) {
    suspend  fun invoke(section: String): Result<List<News>> {
        return newsRepository.getListNews(section)
    }
}