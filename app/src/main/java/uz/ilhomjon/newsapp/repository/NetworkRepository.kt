package uz.ilhomjon.newsapp.repository

import kotlinx.coroutines.flow.flow
import uz.ilhomjon.newsapp.network.ApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getTopHeadlines(country: String, apiKey: String) =
        flow { emit(apiService.getTopHeadLines(country, apiKey)) }

    suspend fun categoryNewsRepository(category: String, apiKey: String) =
        flow { emit(apiService.getCategoryNews(category, apiKey)) }

    suspend fun searchArticleRepository(apiKey: String, query: String) =
        flow { emit(apiService.getNews(apiKey, query)) }
}