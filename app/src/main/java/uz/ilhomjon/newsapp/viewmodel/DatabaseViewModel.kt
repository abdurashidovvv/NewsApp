package uz.ilhomjon.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.database.entity.ArticleEntitiy
import uz.ilhomjon.newsapp.repository.DatabaseRepository
import uz.ilhomjon.newsapp.utils.Resource
import uz.ilhomjon.newsapp.utils.Status
import javax.inject.Inject

class DatabaseViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    private var stateFlow =
        MutableStateFlow<Resource<List<AllCategory>>>(Resource(Status.LOADING, null, "Loading"))

    fun getAllCategory(): StateFlow<Resource<List<AllCategory>>> {
        viewModelScope.launch {
            stateFlow.value = Resource.success(databaseRepository.getAllCategory())
        }
        return stateFlow
    }

    fun addCategory(allCategory: AllCategory) {
        viewModelScope.launch {
            databaseRepository.addCategory(allCategory)
        }
    }

    fun addArticle(articleEntitiy: ArticleEntitiy){
        viewModelScope.launch {
            databaseRepository.addArticle(articleEntitiy)
        }
    }

    private var stateFlow2=
        MutableStateFlow<Resource<List<ArticleEntitiy>>>(Resource(Status.LOADING, null, "Loading"))
    fun getAllArticle():StateFlow<Resource<List<ArticleEntitiy>>>{
        viewModelScope.launch {
            stateFlow2.value= Resource(Status.SUCCESS, databaseRepository.getAllArticle(), "Success")
        }
        return stateFlow2
    }

    fun deleteArticle(articleEntitiy: ArticleEntitiy){
        viewModelScope.launch {
            databaseRepository.deleteArticle(articleEntitiy)
        }
    }
}