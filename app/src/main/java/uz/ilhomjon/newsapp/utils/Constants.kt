package uz.ilhomjon.newsapp.utils

import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.models.categoryFragment.CategoryItem

object Constants {
    const val API_VERSION = "v2/"

    const val DB_NAME = "news_db"

    const val API_KEY="7c04fcfddd224ed6a591ac49e9abb8f2"

    val categoryList = arrayListOf(
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology"
    )

    val categoryItemList= arrayListOf(
        CategoryItem("business", R.drawable.business),
        CategoryItem("entertainment", R.drawable.entertainment),
        CategoryItem("general", R.drawable.general),
        CategoryItem("health", R.drawable.health),
        CategoryItem("science", R.drawable.science),
        CategoryItem("sports", R.drawable.sport),
        CategoryItem("technology", R.drawable.technology)
    )


}

