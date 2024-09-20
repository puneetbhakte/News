package com.example.news

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.adapter.Adapter
import com.example.news.api.ApiInterface
import com.example.news.api.Retrofit
import com.example.news.databinding.ActivityMainBinding
import com.example.news.repository.Repository
import com.example.news.viewmodel.ViewModel
const val Api_key = "fa924cfcba9449579c8b45750d4719ed"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter:Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val service = Retrofit.getInstance().create(ApiInterface::class.java)
        val repository = Repository(service)
        viewModel = ViewModelProvider(this,com.example.news.viewmodel.ViewModelProvider(repository)).get(ViewModel::class.java)


        viewModel.getNews("techcrunch",Api_key).observe(this, Observer {
            val list = it?.articles
            adapter = list?.let { it1 -> Adapter(this, it1) }!!
            binding.rvNews.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
            binding.rvNews.adapter = adapter
            Log.e("check",it.toString())
        })



    }
}