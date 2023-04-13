package uz.ilhomjon.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.squareup.picasso.Picasso
import uz.ilhomjon.newsapp.databinding.ItemContainerLocationBinding
import uz.ilhomjon.newsapp.models.Category.Article

class HomeViewPagerAdapter(var list: ArrayList<Article>) :
    RecyclerView.Adapter<HomeViewPagerAdapter.Vh>() {

    inner class Vh(private val binding: ItemContainerLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: Article) {


            binding.apply {
                Picasso.get().load(article.urlToImage).into(kbvLocation)
                val interpolator = AccelerateDecelerateInterpolator()

                // It is used to change the duration and
                // the interpolator of transitions

                category.text=article.author
                description.text=article.title
                // It is used to change the duration and
                // the interpolator of transitions
                val generator = RandomTransitionGenerator(2000, interpolator)
                kbvLocation.setTransitionGenerator(generator)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemContainerLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}