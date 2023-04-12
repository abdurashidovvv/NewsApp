package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.FragmentHomeBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.utils.Status
import uz.ilhomjon.newsapp.view.adapters.ArticleAdapter
import uz.ilhomjon.newsapp.viewmodel.CategoryNewsViewModel
import uz.ilhomjon.newsapp.viewmodel.TopHeadlinesViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope, ArticleAdapter.CategoryItemCLick {

    override fun onAttach(context: Context) {
        App.appComponent.injectHomeFragment(this)
        super.onAttach(context)
    }


    @Inject
    lateinit var topHeadlinesViewModel: TopHeadlinesViewModel
    @Inject
    lateinit var categoryNewsViewModel: CategoryNewsViewModel
    lateinit var articleAdapter: ArticleAdapter
    private lateinit var list:ArrayList<Article>
    private val binding by lazy{FragmentHomeBinding.inflate(layoutInflater)}
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        list= ArrayList()
        articleAdapter= ArticleAdapter(list, this)
        binding.myRv.adapter=articleAdapter
        launch(Dispatchers.Main) {
            topHeadlinesViewModel.getStateFlow().collect {
                when(it.status){
                    Status.LOADING->{
                        Log.d("@@@", "onCreateView: ${it.message}")
                    }
                    Status.SUCCESS->{
                        if (it.data!=null){
                            list.addAll(it.data.articles)
                            articleAdapter.list=list
                            articleAdapter.notifyDataSetChanged()
                            Log.d("@@@", "onCreateView: ${it.data.articles}")
                        }else{
                            Log.d("@@@", "onCreateView: ${it.message}")
                        }
                    }
                    Status.ERROR->{
                        Log.d("@@@", "onCreateView: ${it.message}")
                    }
                }
            }
        }

        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun onClick(allCategory: AllCategory, position: Int) {

    }
}