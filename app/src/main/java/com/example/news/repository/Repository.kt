package com.example.news.repository

import androidx.lifecycle.MutableLiveData
import com.example.news.api.ApiInterface
import com.example.news.api.Retrofit
import com.example.news.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val apiInterface: ApiInterface) {

     fun getNews(sources: String, api: String): MutableLiveData<News?> {
        val News = MutableLiveData<News?>()
        val service = Retrofit.getInstance().create(ApiInterface::class.java)
        service.getnews(sources,api).enqueue(object : Callback<News>{
            override fun onResponse(p0: Call<News>, p1: Response<News>) {
                if (p1.isSuccessful && p1.body() != null) {

                    try {
                        val baseResponseModel = p1?.body();
                        if (baseResponseModel != null) {
                            News.postValue(baseResponseModel)
                        } else {

                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                } else {

                }
            }

            override fun onFailure(p0: Call<News>, p1: Throwable) {

            }

        })
        return News
    }


}