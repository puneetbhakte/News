package com.example.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.News
import com.example.news.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository):ViewModel() {

    fun getNews(sources:String,api:String): LiveData<News?> {
        val result = MutableLiveData<News?>()

           val response =  repository.getNews(sources,api)
            response.observeForever {
                result.postValue(it)
            }

            return result
    }
}