package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.FragmentHomeBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.utils.Constants
import uz.ilhomjon.newsapp.utils.Constants.API_KEY
import uz.ilhomjon.newsapp.utils.Status
import uz.ilhomjon.newsapp.view.adapters.ArticleAdapter
import uz.ilhomjon.newsapp.view.adapters.HomeCategoryAdapter
import uz.ilhomjon.newsapp.view.adapters.HomeViewPagerAdapter
import uz.ilhomjon.newsapp.viewmodel.CategoryNewsViewModel
import uz.ilhomjon.newsapp.viewmodel.SearchViewModel
import uz.ilhomjon.newsapp.viewmodel.TopHeadlinesViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs
import kotlin.math.log

@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment(), CoroutineScope, HomeCategoryAdapter.CategoryItemCLick {

    override fun onAttach(context: Context) {
        App.appComponent.injectHomeFragment(this)
        super.onAttach(context)
    }


    @Inject
    lateinit var topHeadlinesViewModel: TopHeadlinesViewModel

    @Inject
    lateinit var categoryNewsViewModel: CategoryNewsViewModel

    @Inject
    lateinit var searchViewModel: SearchViewModel

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
            if (category == "business") {
                categoryList.add(AllCategory(category_name = category, isSelected = true))
            } else {
                categoryList.add(AllCategory(category_name = category))
            }
        }
        homeCategoryAdapter = HomeCategoryAdapter(categoryList, this@HomeFragment)
        binding.myTabLayout.adapter = homeCategoryAdapter

        articleAdapter = ArticleAdapter(list, object : ArticleAdapter.CategoryItemCLick {
            override fun onClick(article: Article, position: Int) {
                findNavController().navigate(R.id.infoFragment, bundleOf("article" to article))
            }
        })
        binding.myRv.adapter = articleAdapter

        launch(Dispatchers.Main) {
            getCategoryItem("business")
        }

        //TopHeadlines
        launch(Dispatchers.Main) {
            topHeadlinesViewModel.getStateFlow().collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.rvProgress.visibility = View.VISIBLE
                        Log.d("@homeFragment", "onCreateView: ${it.message}")
                    }
                    Status.SUCCESS -> {
                        binding.rvProgress.visibility = View.GONE
                        if (it.data != null) {
                            list.addAll(it.data.articles)
                            articleAdapter.list = list
                            articleAdapter.notifyDataSetChanged()
                            Log.d("@homeFragment", "onCreateView: ${it.data.articles}")
                        } else {
                            Log.d("@homeFragment", "onCreateView: ${it.message}")
                        }
                    }
                    Status.ERROR -> {
                        Log.d("@homeFragment", "onCreateView: ${it.message}")
                    }
                }
            }
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        binding.searchEdt.addTextChangedListener {
            if (binding.searchEdt.text.toString()!=""){
                binding.myViewpager.visibility=View.GONE
                binding.myTabLayout.visibility=View.GONE
                launch(Dispatchers.Main) {
                    searchViewModel.searchArticle(it.toString()).collect {
                        when (it.status) {
                            Status.LOADING->{
                                Log.d("@log", "onCreateView: loading")
                                binding.viewpagerProgress.visibility=View.VISIBLE
                            }
                            Status.SUCCESS->{
                                if (it.data!=null){
                                    Log.d("@log", "onCreateView: ${it.data}")
                                    val list=it.data.articles
                                    articleAdapter.list=list
                                    binding.viewpagerProgress.visibility=View.GONE
                                }
                            }
                            Status.ERROR->{
                                Log.d("@log", "onCreateView: error")
                            }
                        }
                    }
                    binding.myRv.adapter!!.notifyDataSetChanged()
                }
            }else{
                binding.myViewpager.visibility=View.VISIBLE
                binding.myRv.visibility=View.VISIBLE
                binding.myTabLayout.visibility=View.VISIBLE
            }
        }
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


    @SuppressLint("NotifyDataSetChanged")
    suspend fun getCategoryItem(category: String) {
        categoryNewsViewModel.getCategoryNews(category, API_KEY).collect {
            when (it.status) {
                Status.LOADING -> {
                    binding.viewpagerProgress.visibility = View.VISIBLE
                    Log.d("@@@", "onClick: ${it.message}")
                }
                Status.SUCCESS -> {
                    binding.viewpagerProgress.visibility = View.GONE
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

