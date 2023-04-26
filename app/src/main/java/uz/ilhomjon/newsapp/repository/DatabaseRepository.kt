package uz.ilhomjon.newsapp.repository

import uz.ilhomjon.newsapp.database.dao.CategoryDao
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.database.entity.ArticleEntitiy
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val categoryDao: CategoryDao) {

    suspend fun addCategory(allCategory: AllCategory) = categoryDao.addCategory(allCategory)
    suspend fun getAllCategory(): List<AllCategory> = categoryDao.getAllCategory()

    suspend fun addArticle(articleEntitiy: ArticleEntitiy)=categoryDao.addArticle(articleEntitiy)
    suspend fun getAllArticle():List<ArticleEntitiy> = categoryDao.getAllArticle()
    suspend fun deleteArticle(articleEntitiy: ArticleEntitiy) = categoryDao.deleteArticle(articleEntitiy)
}