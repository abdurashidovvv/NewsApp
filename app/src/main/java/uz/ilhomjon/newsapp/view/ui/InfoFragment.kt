package uz.ilhomjon.newsapp.view.ui

import android.content.Entity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.database.entity.ArticleEntitiy
import uz.ilhomjon.newsapp.databinding.FragmentInfoBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Suppress("DEPRECATION")
class InfoFragment : Fragment(), CoroutineScope {

    private val binding by lazy { FragmentInfoBinding.inflate(layoutInflater) }

    @Inject
    lateinit var databaseViewModel: DatabaseViewModel
    lateinit var list: ArrayList<ArticleEntitiy?>
    lateinit var article: Article
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectInfoFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        article = arguments?.getSerializable("article") as Article

        binding.title.text = article.title
        binding.infoTv.text = article.description
        binding.share.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.url);
            startActivity(Intent.createChooser(shareIntent, "Send to..."))
        }
        Picasso.get().load(article.urlToImage).into(binding.image)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        list = ArrayList<ArticleEntitiy?>()
        launch {
            databaseViewModel.getAllArticle().collectLatest {
                if (it.data != null) {
                    list.addAll(it.data)
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.save.setOnClickListener {
            for (articleEntitiy in list) {
                if (articleEntitiy?.article_title != article.title && articleEntitiy?.article_image != article.urlToImage) {
                    val saveArticle = ArticleEntitiy(
                        article_id = article.source?.id.toString(),
                        article_title = article.title.toString(),
                        article_summary = article.description.toString(),
                        article_image = article.urlToImage.toString()
                    )
                    list.add(saveArticle)
                    databaseViewModel.addArticle(saveArticle)
                    Toast.makeText(binding.root.context, "Save!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val deleteArticle = ArticleEntitiy(
                        articleEntitiy!!.id,
                        articleEntitiy.article_id,
                        articleEntitiy.article_title,
                        articleEntitiy.article_summary,
                        articleEntitiy.article_image
                    )
                    list.remove(deleteArticle)
                    databaseViewModel.deleteArticle(deleteArticle)
                    Toast.makeText(context, "Delete Article ! ", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}