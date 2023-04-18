package uz.ilhomjon.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.ilhomjon.newsapp.databinding.CategoryFragmentCardBinding
import uz.ilhomjon.newsapp.models.categoryFragment.CategoryItem

class CategoryFragmentAdapter(val list:List<CategoryItem>, val categoryFragmentClick: CategoryFragmentClick): RecyclerView.Adapter<CategoryFragmentAdapter.Vh>() {

    inner class Vh(val rvItem:CategoryFragmentCardBinding):ViewHolder(rvItem.root){
        fun onBind(categoryItem: CategoryItem){
            rvItem.categoryTv.text=categoryItem.name
            rvItem.icon.setImageResource(categoryItem.image!!)
            rvItem.root.setOnClickListener {
                categoryFragmentClick.onClick(categoryItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryFragmentCardBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface CategoryFragmentClick{
        fun onClick(categoryItem: CategoryItem)
    }
}