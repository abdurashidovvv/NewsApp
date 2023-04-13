package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.FragmentHomeBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.utils.Constants
import uz.ilhomjon.newsapp.utils.Status
import uz.ilhomjon.newsapp.view.adapters.ArticleAdapter
import uz.ilhomjon.newsapp.view.adapters.HomeCategoryAdapter
import uz.ilhomjon.newsapp.view.adapters.HomeViewPagerAdapter
import uz.ilhomjon.newsapp.viewmodel.CategoryNewsViewModel
import uz.ilhomjon.newsapp.viewmodel.TopHeadlinesViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

class HomeFragment : Fragment(), CoroutineScope, HomeCategoryAdapter.CategoryItemCLick {

    override fun onAttach(context: Context) {
        App.appComponent.injectHomeFragment(this)
        super.onAttach(context)
    }


    @Inject
    lateinit var topHeadlinesViewModel: TopHeadlinesViewModel

    @Inject
    lateinit var categoryNewsViewModel: CategoryNewsViewModel

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var homeCategoryAdapter: HomeCategoryAdapter
    private lateinit var list: ArrayList<Article>
    private lateinit var categoryList: ArrayList<AllCategory>
    private lateinit var categoryArticleList: ArrayList<uz.ilhomjon.newsapp.models.Category.Article>
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        list = ArrayList()
        categoryList = ArrayList()
        categoryArticleList = ArrayList()

        //Viewpager2
        homeViewPagerAdapter = HomeViewPagerAdapter(categoryArticleList)
        binding.myViewpager.adapter = homeViewPagerAdapter
        binding.apply {
            myViewpager.clipToPadding = false
            myViewpager.clipChildren = false
            myViewpager.offscreenPageLimit = 3
            myViewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r: Float = 1 - abs(position)
                page.scaleY = 0.95f + r * 0.05f
            }
            myViewpager.setPageTransformer(compositePageTransformer)
        }


        //category
        val allCategory = Constants.categoryList
        for (category in allCategory) {
            if (category=="business"){
                categoryList.add(AllCategory(category_name = category, isSelected = true))
            }else{
                categoryList.add(AllCategory(category_name = category))
            }
        }
        homeCategoryAdapter = HomeCategoryAdapter(categoryList, this@HomeFragment)
        binding.myTabLayout.adapter = homeCategoryAdapter

        articleAdapter = ArticleAdapter(list, object : ArticleAdapter.CategoryItemCLick {
            override fun onClick(allCategory: AllCategory, position: Int) {
                TODO("Not yet implemented")
            }
        })
        binding.myRv.adapter = articleAdapter

        launch(Dispatchers.Main) {
            getCategoryItem("business")
        }

        //TopHeadlines
        launch(Dispatchers.Main) {
//            getCategoryItem("business")
            topHeadlinesViewModel.getStateFlow().collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.viewpagerProgress.visibility = View.VISIBLE
                        Log.d("@@@", "onCreateView: ${it.message}")
                    }
                    Status.SUCCESS -> {
                        binding.viewpagerProgress.visibility = View.GONE
                        if (it.data != null) {
                            list.addAll(it.data.articles)
                            articleAdapter.list = list
                            articleAdapter.notifyDataSetChanged()
                            Log.d("@@@", "onCreateView: ${it.data.articles}")
                        } else {
                            Log.d("@@@", "onCreateView: ${it.message}")
                        }
                    }
                    Status.ERROR -> {
                        Log.d("@@@", "onCreateView: ${it.message}")
                    }
                }
            }
        }
        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    @SuppressLint("NotifyDataSetChanged")
    override fun categoryClick(allCategory: AllCategory, position: Int) {
        for (category in categoryList) {
            category.isSelected = false
        }
        allCategory.isSelected = !allCategory.isSelected
        Log.d("@@@@", "onClick: $allCategory - $position")
        homeCategoryAdapter.notifyDataSetChanged()

        launch(Dispatchers.Main) {
            getCategoryItem(allCategory.category_name)
        }
    }

    suspend fun getCategoryItem(category:String){
        categoryNewsViewModel.getCategoryNews(category,
            "7c04fcfddd224ed6a591ac49e9abb8f2").collect {
            when (it.status) {
                Status.LOADING -> {
                    binding.rvProgress.visibility = View.VISIBLE
                    Log.d("@@@", "onClick: ${it.message}")
                }
                Status.SUCCESS -> {
                    binding.rvProgress.visibility = View.GONE
                    if (it.data != null) {
                        categoryArticleList.clear()
                        Log.d("@@@", "onClick: ${it.data.articles}")
                        categoryArticleList.addAll(it.data.articles)
                        homeViewPagerAdapter.list = categoryArticleList
                        homeViewPagerAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("@@@", "onClick: ${it.message}")
                    }
                }
                Status.ERROR -> {
                    Log.d("@@@", "onClick: ${it.message}")
                }
            }
        }
    }
}

