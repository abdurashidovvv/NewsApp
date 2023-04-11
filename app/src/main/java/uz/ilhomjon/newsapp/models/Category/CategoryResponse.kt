package uz.ilhomjon.newsapp.models.Category

data class CategoryResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)