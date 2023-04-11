package uz.ilhomjon.newsapp.repository

import uz.ilhomjon.newsapp.database.dao.CategoryDao
import uz.ilhomjon.newsapp.database.entity.AllCategory
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val categoryDao: CategoryDao) {
    suspend fun addCategory(allCategory: AllCategory) = categoryDao.addCategory(allCategory)
    suspend fun getAllCategory(): List<AllCategory> = categoryDao.getAllCategory()
}