package uz.ilhomjon.newsapp.view.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentHomeBinding
import uz.ilhomjon.newsapp.utils.Status
import uz.ilhomjon.newsapp.viewmodel.CategoryNewsViewModel
import uz.ilhomjon.newsapp.viewmodel.TopHeadlinesViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {

    override fun onAttach(context: Context) {
        App.appComponent.injectHomeFragment(this)
        super.onAttach(context)
    }


    @Inject
    lateinit var topHeadlinesViewModel: TopHeadlinesViewModel
    @Inject
    lateinit var categoryNewsViewModel: CategoryNewsViewModel
    private val binding by lazy{FragmentHomeBinding.inflate(layoutInflater)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        launch {
            topHeadlinesViewModel.getStateFlow().collectLatest {
                when(it.status){
                    Status.LOADING->{}
                    Status.SUCCESS->{}
                    Status.ERROR->{}
                }
            }
        }

        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}