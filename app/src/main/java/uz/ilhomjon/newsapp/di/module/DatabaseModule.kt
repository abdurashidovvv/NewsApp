package uz.ilhomjon.newsapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import uz.ilhomjon.newsapp.database.dao.CategoryDao
import uz.ilhomjon.newsapp.database.database.AppDatabase
import uz.ilhomjon.newsapp.utils.Constants.DB_NAME
import javax.inject.Singleton

@Module
class DatabaseModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.allCategoryDao()
}