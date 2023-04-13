package uz.ilhomjon.newsapp.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.HomeCategoryItemBinding

class HomeCategoryAdapter(val list: ArrayList<AllCategory>, val categoryItemCLick: CategoryItemCLick) :
    RecyclerView.Adapter<HomeCategoryAdapter.Vh>() {

    inner class Vh(private val rvItem: HomeCategoryItemBinding) : RecyclerView.ViewHolder(rvItem.root) {
        fun onBind(allCategory: AllCategory, position: Int) {
            rvItem.tv.text=allCategory.category_name
            if (allCategory.isSelected){
                rvItem.card.setBackgroundColor(Color.parseColor("#475AD7"))
            }else{
                rvItem.card.setBackgroundColor(Color.parseColor("#F3F4F6"))
            }
            rvItem.root.setOnClickListener {
                categoryItemCLick.categoryClick(allCategory, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HomeCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface CategoryItemCLick{
        fun categoryClick(allCategory: AllCategory, position: Int)
    }
}