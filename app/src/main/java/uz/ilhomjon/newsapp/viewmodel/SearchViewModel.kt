package uz.ilhomjon.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.models.TopHeadlines.TopHeadlinesResponse
import uz.ilhomjon.newsapp.repository.NetworkRepository
import uz.ilhomjon.newsapp.utils.Constants.API_KEY
import uz.ilhomjon.newsapp.utils.Resource
import uz.ilhomjon.newsapp.utils.Status
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {
    private val stateFlow =
        MutableStateFlow<Resource<TopHeadlinesResponse>>(Resource(Status.LOADING, null, "Loading"))

    fun searchArticle(
        query: String,
    ): StateFlow<Resource<TopHeadlinesResponse>> {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchArticleRepository(API_KEY, query).catch {
                stateFlow.value = Resource(Status.ERROR, null, "Error")
            }.collect {
                if (it.isSuccessful) {
                    stateFlow.value = Resource(Status.SUCCESS, it.body(), "Success")
                } else {
                    stateFlow.value = Resource(Status.ERROR, null, "Response Error")
                }
            }
        }
        return stateFlow
    }
}