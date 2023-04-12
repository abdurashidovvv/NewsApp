package uz.ilhomjon.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.ArticleAdapterItemBinding
import uz.ilhomjon.newsapp.databinding.CategoryRvItemBinding
import uz.ilhomjon.newsapp.models.Category.Article

class ArticleAdapter(val list: List<Article>, val categoryItemCLick: CategoryItemCLick) :
    RecyclerView.Adapter<ArticleAdapter.Vh>() {

    inner class Vh(private val rvItem: ArticleAdapterItemBinding) : RecyclerView.ViewHolder(rvItem.root) {
        fun onBind(article: Article, position: Int) {
            rvItem.category.text=article.content
            rvItem.title.text=article.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ArticleAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface CategoryItemCLick{
        fun onClick(allCategory: AllCategory, position: Int)
    }
}