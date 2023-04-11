package uz.ilhomjon.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.database.dao.CategoryDao
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.utils.Resource
import uz.ilhomjon.newsapp.utils.Status
import javax.inject.Inject

class DatabaseViewModel @Inject constructor(private val categoryDao: CategoryDao) : ViewModel() {

    private var stateFlow =
        MutableStateFlow<Resource<List<AllCategory>>>(Resource(Status.LOADING, null, "Loading"))

    fun getAllCategory(): StateFlow<Resource<List<AllCategory>>> {
        viewModelScope.launch {
            stateFlow.value= Resource.success(categoryDao.getAllCategory())
        }
        return stateFlow
    }

    fun addCategory(allCategory: AllCategory){
        viewModelScope.launch {
            categoryDao.addCategory(allCategory)
        }
    }
}