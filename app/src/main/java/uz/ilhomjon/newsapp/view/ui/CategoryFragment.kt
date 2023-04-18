package uz.ilhomjon.newsapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentCategoryBinding
import uz.ilhomjon.newsapp.models.categoryFragment.CategoryItem
import uz.ilhomjon.newsapp.utils.Constants
import uz.ilhomjon.newsapp.view.adapters.CategoryFragmentAdapter

class CategoryFragment : Fragment(), CategoryFragmentAdapter.CategoryFragmentClick {

    private val binding by lazy { FragmentCategoryBinding.inflate(layoutInflater) }
    private lateinit var categoryFragmentAdapter: CategoryFragmentAdapter
    private lateinit var list: ArrayList<CategoryItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        list= ArrayList()
        list.addAll(Constants.categoryItemList)
        categoryFragmentAdapter= CategoryFragmentAdapter(list, this)
        binding.myRv.adapter=categoryFragmentAdapter

        return binding.root
    }

    override fun onClick(categoryItem: CategoryItem) {
        findNavController().navigate(R.id.selectFragment)
    }
}