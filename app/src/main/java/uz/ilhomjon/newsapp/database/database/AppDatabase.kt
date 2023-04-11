package uz.ilhomjon.newsapp.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.ilhomjon.newsapp.database.dao.CategoryDao
import uz.ilhomjon.newsapp.database.entity.AllCategory

@Database(entities = [AllCategory::class], version = 1)
abstract class AppDatabase :RoomDatabase(){

    abstract fun allCategoryDao():CategoryDao
}