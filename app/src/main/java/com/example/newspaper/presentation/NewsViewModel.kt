package com.example.newspaper.presentation

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newspaper.R
import com.example.newspaper.domain.newscase.GetNewsListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(
    application: Application,
    private val getNewsListUseCase: GetNewsListUseCase
): AndroidViewModel(application) {
    private val _liveData = MutableLiveData<List<RecyclerNews>>()
    private val _error = MutableLiveData<String?>()
    private val _backgroundColorLiveData = MutableLiveData<Int>()
    private val _loading = MutableLiveData<Boolean>(false)
    private var networkJob: Job? = null

    val liveData: LiveData<List<RecyclerNews>> = _liveData
    val error: LiveData<String?> = _error
    val loading : LiveData<Boolean> = _loading
    val backgroundColorLiveData: LiveData<Int> = _backgroundColorLiveData

    init {
        choosingSectionNews(SectionNews.HOME)
    }

    private fun setUpNews (section: String, color: Int) {
        _backgroundColorLiveData.value = color
        _liveData.value = listOf()
        _error.value = null
        networkJob?.cancel()
        networkJob = viewModelScope.launch {
            _loading.value = true
            val result = getNewsListUseCase.invoke(section)
            if (result.isSuccess) {
                val list = result.getOrNull()?.map {
                    RecyclerNews(
                        titleNews = it.title,
                        byNews = it.byline,
                        abstractNews = it.abstract,
                        urlImage = it.multimedia,
                        color = color,
                        urlNews = it.url,
                    )
                }
                if(list == null) {
                    handleError(getApplication<Application>().getString(R.string.empty_news_error))
                } else {
                    _liveData.value = list
                    _loading.value = false
                }
            } else {
                handleError(result.exceptionOrNull()?.localizedMessage)
            }
        }
    }

    private fun handleError(error: String?) {
        this._error.value = error ?: getApplication<Application>().getString(R.string.unknown_error)
        _loading.value = false
    }

    fun choosingSectionNews(sectionNews: SectionNews) {
        when(sectionNews) {
            SectionNews.HOME -> setUpNews("home", ContextCompat.getColor(getApplication(), R.color.white))
            SectionNews.WORLD -> setUpNews("world", ContextCompat.getColor(getApplication(), R.color.worldNews))
            SectionNews.US -> setUpNews("us", ContextCompat.getColor(getApplication(), R.color.usNews))
            SectionNews.ARTS -> setUpNews("arts", ContextCompat.getColor(getApplication(), R.color.artNews))
            SectionNews.SCIENCE -> setUpNews("science", ContextCompat.getColor(getApplication(), R.color.scienceNews))
        }
    }
}