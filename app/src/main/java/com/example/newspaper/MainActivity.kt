package com.example.newspaper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newspaper.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

val retrofit = Retrofit.Builder().baseUrl("https://api.nytimes.com/svc/topstories/v2/")
    .addConverterFactory(GsonConverterFactory.create()).build()
val newsApi = retrofit.create(NewsAPI::class.java)

object A {

}

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var newsList: ArrayList<RecyclerNews>

    private  lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsList = arrayListOf()

        var backColor = sharedPref.getInt("backColor",R.color.white)
        var color = sharedPref.getInt("color", ContextCompat.getColor(this@MainActivity, R.color.white))
        var section = sharedPref.getString("section", "home")

        binding.main.setBackgroundResource(backColor)
        setUpNews(section!!, color)

        binding.home.setOnClickListener {
            newsList = arrayListOf()
            backColor = R.color.white
            color = ContextCompat.getColor(this@MainActivity, R.color.white)
            section = "home"
            editor.apply {
                putInt("backColor", backColor)
                putInt("color", color)
                putString("section", section)
                apply()
            }
            binding.main.setBackgroundResource(backColor)
            setUpNews(section!!, R.color.white)
        }
        binding.world.setOnClickListener {
            newsList = arrayListOf()
            backColor = R.color.worldNews
            color = ContextCompat.getColor(this@MainActivity, R.color.worldNews)
            section = "world"
            editor.apply {
                putInt("backColor", backColor)
                putInt("color", color)
                putString("section", section)
                apply()
            }
            binding.main.setBackgroundResource(backColor)
            setUpNews(section!!, color)
        }
        binding.us.setOnClickListener {
            newsList = arrayListOf()
            backColor = R.color.usNews
            color = ContextCompat.getColor(this@MainActivity, R.color.usNews)
            section = "us"
            editor.apply {
                putInt("backColor", backColor)
                putInt("color", color)
                putString("section", section)
                apply()
            }
            binding.main.setBackgroundResource(backColor)
            setUpNews(section!!, color)
        }
        binding.arts.setOnClickListener {
            newsList = arrayListOf()
            backColor = R.color.artNews
            color = ContextCompat.getColor(this@MainActivity, R.color.artNews)
            section = "arts"
            editor.apply {
                putInt("backColor", backColor)
                putInt("color", color)
                putString("section", section)
                apply()
            }
            binding.main.setBackgroundResource(backColor)
            setUpNews(section!!, color)
        }
        binding.science.setOnClickListener {
            newsList = arrayListOf()
            backColor = R.color.scienceNews
            color = ContextCompat.getColor(this@MainActivity, R.color.scienceNews)
            section = "science"
            editor.apply {
                putInt("backColor", backColor)
                putInt("color", color)
                putString("section", section)
                apply()
            }
            binding.main.setBackgroundResource(backColor)
            setUpNews(section!!, color)
        }
    }



    private fun setUpNews (section: String, color: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val news = newsApi.getNewsBySection(section)
                runOnUiThread {
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
                    recyclerView.adapter = AdapterNews(this@MainActivity, newsList)
                }
            }  catch(e: UnknownHostException){
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Соединение с сетью отсуствует", Toast.LENGTH_LONG).show()
                }
            } catch(e: Exception){
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Отправлено множество запросов в сеть. Пожалуйста подождите или перезапустите приложение", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
