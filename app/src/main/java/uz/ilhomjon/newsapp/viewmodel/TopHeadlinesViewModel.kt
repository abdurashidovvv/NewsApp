package uz.ilhomjon.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.models.TopHeadlines.TopHeadlinesResponse
import uz.ilhomjon.newsapp.repository.NetworkRepository
import uz.ilhomjon.newsapp.utils.Resource
import uz.ilhomjon.newsapp.utils.Status
import javax.inject.Inject


class TopHeadlinesViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {
    private val stateFlow =
        MutableStateFlow<Resource<TopHeadlinesResponse>>(Resource(Status.LOADING, null, "Loading"))

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch {
            repository.getTopHeadlines("us", "7c04fcfddd224ed6a591ac49e9abb8f2").catch {
                stateFlow.value = Resource(Status.ERROR, null, it.message.toString())
            }.collect {
                if (it.isSuccessful) {
                    stateFlow.value =
                        Resource(Status.SUCCESS, it.body(), "Success")
                } else {
                    stateFlow.value = Resource(Status.ERROR, null, it.errorBody().toString())
                }
            }
        }
    }

    fun getStateFlow(): StateFlow<Resource<TopHeadlinesResponse>> = stateFlow
}