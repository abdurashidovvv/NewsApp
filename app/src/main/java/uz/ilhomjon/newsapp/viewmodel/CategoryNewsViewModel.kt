package uz.ilhomjon.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.models.Category.CategoryResponse
import uz.ilhomjon.newsapp.repository.NetworkRepository
import uz.ilhomjon.newsapp.utils.Resource
import uz.ilhomjon.newsapp.utils.Status
import javax.inject.Inject

class CategoryNewsViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private var stateFlow =
        MutableStateFlow<Resource<CategoryResponse>>(Resource(Status.LOADING, null, "Loading"))

    fun getCategoryNews(
        category: String,
        apiKey: String,
    ): StateFlow<Resource<CategoryResponse>> {
        viewModelScope.launch(Dispatchers.IO) {
            repository.categoryNewsRepository(category, apiKey).catch {
                stateFlow.value = Resource(Status.ERROR, null, it.message.toString())
            }.collect {
                if (it.isSuccessful) {
                    stateFlow.value = Resource(Status.SUCCESS, it.body(), "Success")
                } else {
                    stateFlow.value = Resource(Status.ERROR, null, it.errorBody().toString())
                }
            }
        }
        return stateFlow
    }
}