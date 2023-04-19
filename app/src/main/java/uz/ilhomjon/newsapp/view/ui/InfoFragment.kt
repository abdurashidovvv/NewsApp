package uz.ilhomjon.newsapp.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import uz.ilhomjon.newsapp.databinding.FragmentInfoBinding
import uz.ilhomjon.newsapp.models.TopHeadlines.Article
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import javax.inject.Inject

@Suppress("DEPRECATION")
class InfoFragment : Fragment() {

    private val binding by lazy { FragmentInfoBinding.inflate(layoutInflater) }

    @Inject
    lateinit var databaseViewModel: DatabaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val article = arguments?.getSerializable("article") as Article



        binding.title.text = article.title
        binding.infoTv.text = article.description
        Picasso.get().load(article.urlToImage).into(binding.image)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}