package com.example.news.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.DetailNews
import com.example.news.R
import com.example.news.databinding.SingleViewBinding
import com.example.news.model.Article
import com.example.news.model.News

class Adapter( var context: Context,  var data:List<Article>):RecyclerView.Adapter<Adapter.viewholder>() {

    inner class viewholder(val binding: SingleViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.viewholder {
        val inflater = LayoutInflater.from(context)
        val binding = SingleViewBinding.inflate(inflater,parent,false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.viewholder, position: Int) {
        val currentList = data[position]
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        holder.binding.name.text = currentList.author
        holder.binding.mainTitle.text = currentList.title
        Glide.with(context)
            .load(currentList.urlToImage)
            .placeholder(R.drawable.wait)
            .error(R.drawable.images)
            .into(holder.binding.profileImage)
        holder.itemView.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("auther", currentList.author)
            editor.putString("title", currentList.title)
            editor.putString("desp", currentList.description)
            editor.apply()
            val intent = Intent(context,DetailNews::class.java)
            intent.putExtra("position",position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return data.size
    }
}