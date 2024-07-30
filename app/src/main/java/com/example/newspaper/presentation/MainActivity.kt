package com.example.newspaper.presentation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterNews(this@MainActivity)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this, MainFactory(application)).get(NewsViewModel::class.java)
        viewModel.liveData.observe(this) { newsList ->
            adapter.setNewList(newsList)
        }
        viewModel.backgroundColorLiveData.observe(this) { color ->
            binding.main.setBackgroundColor(color)
        }
        val animation: AnimationDrawable? = binding.load.background as? AnimationDrawable
        viewModel.loading.observe(this) { loading ->
            if(loading) {
                animation?.start()
                binding.load.visibility = View.VISIBLE
            } else {
                animation?.stop()
                binding.load.visibility = View.GONE
            }
        }
        viewModel.error.observe(this) { error ->
            binding.error.text = error
        }

        binding.home.setOnClickListener {
            viewModel.choosingSectionNews(SectionNews.HOME)
        }
        binding.world.setOnClickListener {
            viewModel.choosingSectionNews(SectionNews.WORLD)
        }
        binding.us.setOnClickListener {
            viewModel.choosingSectionNews(SectionNews.US)
        }
        binding.arts.setOnClickListener {
            viewModel.choosingSectionNews(SectionNews.ARTS)
        }
        binding.science.setOnClickListener {
            viewModel.choosingSectionNews(SectionNews.SCIENCE)
        }

    }
}
