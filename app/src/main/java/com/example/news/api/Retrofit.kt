package com.example.news.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
object Retrofit {
    private const val BASE_URL = "https://newsapi.org/"

    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}