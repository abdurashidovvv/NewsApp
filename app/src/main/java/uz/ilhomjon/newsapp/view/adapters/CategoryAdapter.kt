package uz.ilhomjon.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.CategoryRvItemBinding

class CategoryAdapter(val list: ArrayList<AllCategory>, val categoryItemCLick: CategoryItemCLick) :
    RecyclerView.Adapter<CategoryAdapter.Vh>() {

    inner class Vh(private val rvItem: CategoryRvItemBinding) : ViewHolder(rvItem.root) {
        fun onBind(allCategory: AllCategory, position: Int) {
            rvItem.tv.text=allCategory.category_name
            if (allCategory.isSelected){
                rvItem.checkImage.setImageResource(R.drawable.check)
            }else{
                rvItem.checkImage.setImageResource(0)
            }
            rvItem.checkbox.setOnClickListener {
                categoryItemCLick.onClick(allCategory, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface CategoryItemCLick{
        fun onClick(allCategory: AllCategory, position: Int)
    }
}