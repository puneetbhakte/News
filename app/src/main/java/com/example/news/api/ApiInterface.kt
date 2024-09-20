package com.example.news.api

import com.example.news.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v2/top-headlines")
    fun getnews(@Query("sources") sources : String, @Query("apiKey") key : String):Call<News>

}