package com.example.newspaper.data.repository

import com.example.newspaper.data.datasource.NewsDataSource
import com.example.newspaper.domain.model.News
import com.example.newspaper.domain.repository.NewsRepository

class NewsRepositoryImpl: NewsRepository {
    override suspend fun getListNews(section: String): Result<List<News>> {
        val result = NewsDataSource.getNewsList(section)
        return result.map { response ->
            response.results.map {
                News(
                    title = it.title,
                    abstract = it.abstract,
                    url = it.url.let { url ->
                        if (url == "null") {
                            "https://www.nytimes.com/"
                        } else {
                            url
                        }
                    },
                    byline= it.byline,
                    multimedia = it.multimedia?.getOrNull(0)?.url ?: "https://media.licdn.com/dms/image/C5112AQHF774FQQlF3Q/article-cover_image-shrink_600_2000/0/1520111068550?e=2147483647&v=beta&t=ZB0WgXtvphu_-GENI__W1YAChf32k5VDJhBYGbCd00A"
                )
            }
        }
    }
}