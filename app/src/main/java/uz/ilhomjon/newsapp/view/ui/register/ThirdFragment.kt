package uz.ilhomjon.newsapp.view.ui.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.FragmentThirdBinding
import uz.ilhomjon.newsapp.utils.Constants
import uz.ilhomjon.newsapp.view.adapters.CategoryAdapter
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import javax.inject.Inject

class ThirdFragment : Fragment(), CategoryAdapter.CategoryItemCLick {

    @Inject
    lateinit var databaseViewModel: DatabaseViewModel
    private val binding by lazy { FragmentThirdBinding.inflate(layoutInflater) }
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var list: ArrayList<AllCategory>

    override fun onAttach(context: Context) {
        App.appComponent.injectThirdFragment(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        list = ArrayList()
        val allCategory = Constants.categoryList
        for (item in allCategory) {
            val category = AllCategory(category_name = item)
            list.add(category)
        }

        binding.nextBtn.setOnClickListener {
            for (category in list) {
                databaseViewModel.addCategory(category)
            }
            findNavController().navigate(R.id.mainFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        categoryAdapter = CategoryAdapter(list, this)
        binding.myRv.adapter = categoryAdapter
    }

    override fun onClick(allCategory: AllCategory, position: Int) {
        allCategory.isSelected = !allCategory.isSelected
        Log.d("@@@@", "onClick: $allCategory - $position")
        categoryAdapter.notifyItemChanged(position)
    }
}