package uz.ilhomjon.newsapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.ilhomjon.newsapp.models.TopHeadlines.Article

@Entity
data class ArticleEntitiy(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var article_id:String,
    var article_title:String,
    var article_summary:String,
    var article_image:String
)