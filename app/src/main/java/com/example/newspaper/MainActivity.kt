package com.example.newspaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    private  lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterNews(this@MainActivity,)
        recyclerView.adapter = adapter
        viewModel.liveData.observe(this) { newsList ->
            adapter.setNewList(newsList)
        }
        viewModel.backgroundColorLiveData.observe(this) { color ->
            binding.main.setBackgroundColor(color)
        }

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
