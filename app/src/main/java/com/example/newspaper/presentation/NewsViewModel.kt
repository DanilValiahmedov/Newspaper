package com.example.newspaper.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newspaper.R
import com.example.newspaper.domain.newscase.GetNewsListUseCase
import kotlinx.coroutines.launch

class NewsViewModel(
    application: Application,
    private val getNewsListUseCase: GetNewsListUseCase
): AndroidViewModel(application) {
    private val _liveData = MutableLiveData<List<RecyclerNews>>()
    private val _error = MutableLiveData<String?>()
    private val _backgroundColorLiveData = MutableLiveData<Int>()

    val liveData: LiveData<List<RecyclerNews>> = _liveData
    val error: LiveData<String?> = _error
    val backgroundColorLiveData: LiveData<Int> = _backgroundColorLiveData

    fun setUpNews (section: String, color: Int) {
        _backgroundColorLiveData.value = color
        viewModelScope.launch {
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
                }
            } else {
                handleError(result.exceptionOrNull()?.localizedMessage)
            }
        }
    }

    private fun handleError(error: String?) {
        this._error.value = error ?: getApplication<Application>().getString(R.string.unknown_error)
    }
}