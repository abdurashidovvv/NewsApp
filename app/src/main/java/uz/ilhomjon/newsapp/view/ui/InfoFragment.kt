package uz.ilhomjon.newsapp.view.ui

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
            startActivity(Intent.createChooser(shareIntent, "Send to..."))
        }
        Picasso.get().load(article.urlToImage).into(binding.image)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        list = ArrayList()

        launch(Dispatchers.Main) {
            databaseViewModel.getAllArticle().collectLatest {
                if (it.data != null) {
                    it.data.forEach { articleEntity ->
                        if (articleEntity.article_image == article.urlToImage && articleEntity.article_title == article.title) {
                            binding.save.setAltImageResource(R.drawable.bookmark3)
                            binding.save.setImageResource(R.drawable.bookmark3)
                        }
                    }
                }
            }
        }

        launch {
            loadData()
        }

        val saveArticle = ArticleEntitiy(
            article_id = article.source?.id.toString(),
            article_title = article.title.toString(),
            article_summary = article.description.toString(),
            article_image = article.urlToImage.toString()
        )
        binding.save.setOnClickListener {
            if (checkArticle()) {
                databaseViewModel.deleteArticle(saveArticle)
                list.remove(saveArticle)
                Log.d("@infoFragment", "onCreateView: ")
                Toast.makeText(context, "Delete Article !", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                databaseViewModel.addArticle(saveArticle)
                list.add(saveArticle)
                Toast.makeText(context, "Save Article !", Toast.LENGTH_SHORT).show()
            }

        }


        return binding.root
    }

    private fun checkArticle(): Boolean {
        list.forEach {
            if (it?.article_title == article.title || it?.article_image == article.urlToImage) return true
        }
        return false
    }

    private suspend fun loadData() {
        databaseViewModel.getAllArticle().catch {
            Log.d("@infoFragment", "loadData: ${it.message}")
        }.collectLatest {
            if (it.data != null) {
                list.addAll(it.data)
            }
        }
    }


    override val coroutineContext: CoroutineContext
        get() = Job()
}