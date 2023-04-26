package uz.ilhomjon.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.database.entity.ArticleEntitiy

@Dao
interface CategoryDao {

    @Insert
    suspend fun addCategory(allCategory: AllCategory)

    @Query("select *from allcategory")
    suspend fun getAllCategory():List<AllCategory>

    @Insert
    suspend fun addArticle(articleEntitiy: ArticleEntitiy)

    @Query("select *from articleentitiy")
    suspend fun getAllArticle():List<ArticleEntitiy>

    @Delete
    suspend fun deleteArticle(articleEntitiy: ArticleEntitiy)
}