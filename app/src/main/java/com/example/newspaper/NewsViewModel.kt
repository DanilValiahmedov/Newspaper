package com.example.newspaper

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class NewsViewModel (application: Application): AndroidViewModel(application) {
    val liveData = MutableLiveData<ArrayList<RecyclerNews>>()
    val backgroundColorLiveData = MutableLiveData<Int>()

    var newsList: ArrayList<RecyclerNews>  = arrayListOf()

    private val retrofit = Retrofit.Builder().baseUrl("https://api.nytimes.com/svc/topstories/v2/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val newsApi = retrofit.create(NewsAPI::class.java)


    fun setUpNews (section: String, color: Int) {

        viewModelScope.launch {
            try {
                val news = newsApi.getNewsBySection(section)
                withContext(Dispatchers.Main) {
                    //изменение заднего фона
                    backgroundColorLiveData.value = color
                    newsList.clear()
                    for (i in 0 until news.num_results) {
                        try {
                            val titleNews = news.results[i].title
                            val byNews = news.results[i].byline
                            val abstractNews = news.results[i].abstract
                            var urlNews = news.results[i].url
                            val urlImage = news.results[i].multimedia[1].url
                            if (news.results[i].url == "null") {
                                urlNews = "https://www.nytimes.com/"
                            }
                            newsList.add(RecyclerNews(titleNews, byNews, abstractNews, urlImage, color, urlNews))
                        } catch (e: Exception) {
                            val titleNews = "Technical problems"
                            val byNews = "Technical problems"
                            val abstractNews = "Sorry, this news could not load due to technical problems"
                            val urlNews = "https://www.nytimes.com/"
                            val urlImage = "https://media.licdn.com/dms/image/C5112AQHF774FQQlF3Q/article-cover_image-shrink_600_2000/0/1520111068550?e=2147483647&v=beta&t=ZB0WgXtvphu_-GENI__W1YAChf32k5VDJhBYGbCd00A"
                            newsList.add(RecyclerNews(titleNews, byNews, abstractNews, urlImage, color, urlNews))
                        }
                    }
                    liveData.value = newsList
                }
            }  catch(e: UnknownHostException){
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Соединение с сетью отсуствует", Toast.LENGTH_LONG).show()
                }
            } catch(e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Отправлено множество запросов в сеть. Пожалуйста подождите или перезапустите приложение", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




}