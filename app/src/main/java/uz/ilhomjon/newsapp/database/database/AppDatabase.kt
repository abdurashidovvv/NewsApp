package uz.ilhomjon.newsapp.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.ilhomjon.newsapp.database.dao.CategoryDao
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.database.entity.ArticleEntitiy

@Database(entities = [AllCategory::class, ArticleEntitiy::class], version = 3)
abstract class AppDatabase :RoomDatabase(){

    abstract fun allCategoryDao():CategoryDao
}