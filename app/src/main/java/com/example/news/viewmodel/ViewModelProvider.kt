package com.example.news.viewmodel

import com.example.news.repository.Repository
import androidx.lifecycle.ViewModelProvider

class ViewModelProvider(private val repository:Repository): ViewModelProvider.Factory {

    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(repository) as T
    }

}