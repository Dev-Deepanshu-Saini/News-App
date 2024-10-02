package com.example.newsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel:ViewModel() {

    private val _articles=MutableLiveData<List<Article>>()
    val article: LiveData<List<Article>> = _articles

    init {
        fetchNewsTopHeadLines()
    }

    fun fetchNewsTopHeadLines(category:String="GENERAL"){
        val newsApiClient=NewsApiClient(Const.API_KEY)
        val request=TopHeadlinesRequest.Builder().language("en").category(category).build()
        newsApiClient.getTopHeadlines(request,object :NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let{
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    Log.i("News Api response Failed",throwable.localizedMessage)
                }
            }

        })
    }

    fun fetchEveryThingWithQuery(query:String){
        val newsApiClient=NewsApiClient(Const.API_KEY)
        val request=EverythingRequest.Builder().language("en").q(query).build()
        newsApiClient.getEverything(request,object :NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let{
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    Log.i("News Api response Failed",throwable.localizedMessage)
                }
            }

        })
    }
}