package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentSelectBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.utils.Status
import uz.ilhomjon.newsapp.view.adapters.ArticleAdapter
import uz.ilhomjon.newsapp.viewmodel.TopHeadlinesViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class SelectFragment : Fragment(), ArticleAdapter.CategoryItemCLick, CoroutineScope {

    private val binding by lazy { FragmentSelectBinding.inflate(layoutInflater) }
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var list: ArrayList<Article>

    @Inject
    lateinit var topHeadlinesViewModel: TopHeadlinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectSelectFragment(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val title = arguments?.getString("category")
        binding.title.text = title

        list = ArrayList()
        articleAdapter = ArticleAdapter(list, this)
        launch(Dispatchers.Main) {
            topHeadlinesViewModel.getStateFlow().collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.myProgress.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.myProgress.visibility = View.GONE
                        if (it.data != null) {
                            articleAdapter.list = it.data.articles
                            articleAdapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(context, "Error Response", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.myRv.adapter = articleAdapter
        return binding.root
    }

    override fun onClick(article: Article, position: Int) {
        findNavController().navigate(R.id.infoFragment, bundleOf("article" to article))
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}