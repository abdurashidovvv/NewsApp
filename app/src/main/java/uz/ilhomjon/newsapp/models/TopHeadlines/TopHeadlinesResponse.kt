package uz.ilhomjon.newsapp.models.TopHeadlines

data class TopHeadlinesResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)