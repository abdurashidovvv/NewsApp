package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.database.entity.ArticleEntitiy
import uz.ilhomjon.newsapp.databinding.FragmentSavedBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.view.adapters.ArticleAdapter
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SavedFragment : Fragment(), CoroutineScope, ArticleAdapter.CategoryItemCLick {

    private val binding by lazy { FragmentSavedBinding.inflate(layoutInflater) }

    @Inject
    lateinit var databaseViewModel: DatabaseViewModel
    lateinit var list: ArrayList<Article>
    lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectBookmarkFragment(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        list = ArrayList()
        articleAdapter = ArticleAdapter(list, this)
        binding.myRv.adapter=articleAdapter

        launch(Dispatchers.Main) {
            databaseViewModel.getAllArticle().catch {
                Log.d("@saveFragment", "onCreateView: ${it.message}")
            }.collectLatest {
                Log.d("@saveFragment", "onCreateView: ${it.data}")
                if (it.data != null) {
                    it.data.forEach { articleEntity ->
                        val article = Article(
                            author = null,
                            content = null,
                            description = articleEntity.article_summary,
                            publishedAt = null,
                            source = null,
                            title = articleEntity.article_title,
                            url = null,
                            urlToImage = articleEntity.article_image
                        )
                        list.add(article)
                        Log.d("@saveFragment", "List: $list ")
                    }
                    articleAdapter.list=list
                    articleAdapter.notifyDataSetChanged()
                }
            }
        }
        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun onClick(article: Article, position: Int) {
        findNavController().navigate(R.id.infoFragment, bundleOf("article" to article))
    }
}