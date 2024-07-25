package com.example.newspaper.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newspaper.data.repository.NewsRepositoryImpl
import com.example.newspaper.domain.newscase.GetNewsListUseCase

class MainFactory(val application: Application):
ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(application, GetNewsListUseCase(NewsRepositoryImpl())) as T
    }

}