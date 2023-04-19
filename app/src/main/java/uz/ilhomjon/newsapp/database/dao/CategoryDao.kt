package uz.ilhomjon.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.ilhomjon.newsapp.database.entity.AllCategory

@Dao
interface CategoryDao {

    @Insert
    suspend fun addCategory(allCategory: AllCategory)

    @Query("select *from allcategory")
    suspend fun getAllCategory():List<AllCategory>

}