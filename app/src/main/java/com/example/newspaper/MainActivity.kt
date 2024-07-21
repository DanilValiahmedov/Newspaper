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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel


    private  lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.liveData.observe(this, { newsList ->
            recyclerView.adapter = AdapterNews(this@MainActivity, newsList)
        })
        viewModel.backgroundColorLiveData.observe(this, { color ->
            binding.main.setBackgroundColor(color)
        })

        binding.home.setOnClickListener {
            viewModel.setUpNews("home", ContextCompat.getColor(this@MainActivity, R.color.white))
        }
        binding.world.setOnClickListener {
            viewModel.setUpNews("world", ContextCompat.getColor(this@MainActivity, R.color.worldNews))
        }
        binding.us.setOnClickListener {
            viewModel.setUpNews("us", ContextCompat.getColor(this@MainActivity, R.color.usNews))
        }
        binding.arts.setOnClickListener {
            viewModel.setUpNews("arts", ContextCompat.getColor(this@MainActivity, R.color.artNews))
        }
        binding.science.setOnClickListener {
            viewModel.setUpNews("science", ContextCompat.getColor(this@MainActivity, R.color.scienceNews))
        }

    }
}
