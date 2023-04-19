package uz.ilhomjon.newsapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.ilhomjon.newsapp.models.Category.CategoryResponse
import uz.ilhomjon.newsapp.models.TopHeadlines.TopHeadlinesResponse


interface ApiService {

    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String,
        @Query("apiKey") apiKey:String
    ):Response<TopHeadlinesResponse>

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ):Response<CategoryResponse>

    @GET("everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String
    ):Response<TopHeadlinesResponse>

}
