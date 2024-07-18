package com.example.newspaper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterNews(private val context: Context,private val newsList: List<RecyclerNews>): RecyclerView.Adapter<AdapterNews.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]
        holder.titleNews.text = item.titleNews
        holder.byNews.text = item.byNews
        holder.abstractNews.text = item.abstractNews
        Glide.with(holder.itemView.context).load(item.urlImage).into(holder.imageNews)
        holder.cardView.setCardBackgroundColor(item.color)
        holder.learnMore.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.urlNews))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleNews: TextView = itemView.findViewById(R.id.titleNews)
        val byNews: TextView = itemView.findViewById(R.id.byNews)
        val abstractNews: TextView = itemView.findViewById(R.id.abstractNews)
        val imageNews: ImageView = itemView.findViewById(R.id.imageNews)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val learnMore: TextView = itemView.findViewById(R.id.learnMore)
    }
}
