package com.example.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.news.api.ApiInterface
import com.example.news.api.Retrofit
import com.example.news.databinding.ActivityDetailNewsBinding
import com.example.news.repository.Repository
import com.example.news.viewmodel.ViewModel
import com.example.news.viewmodel.ViewModelProvider


class DetailNews : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var newsUrl = ""

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val auther = sharedPreferences.getString("auther", "defaultName")
        val title = sharedPreferences.getString("title", "defaultName")
        val desp = sharedPreferences.getString("desp", "defaultName")
        val num = intent.getIntExtra("position",0)
        binding.mainDesp.text = desp
        binding.mainTitle.text = title
        binding.name.text = auther

        binding.mainLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(newsUrl))
            startActivity(i)
        }

        val service = Retrofit.getInstance().create(ApiInterface::class.java)
        val repository = Repository(service)
        viewModel = androidx.lifecycle.ViewModelProvider(this, ViewModelProvider(repository)).get(ViewModel::class.java)

        viewModel.getNews("techcrunch", Api_key).observe(this, Observer {
            val list = it?.articles
            Glide.with(this)
                .load(list?.get(num)?.urlToImage)
                .placeholder(R.drawable.wait)
                .error(R.drawable.images)
                .into(binding.mainImage)
            binding.mainLink.text = list?.get(num)?.url ?: "link is not available"
            newsUrl = list?.get(num)?.url ?: "link is not available"
            binding.mainPublish.text = list?.get(num)?.publishedAt ?: "publish is not available"

        })

    }
}