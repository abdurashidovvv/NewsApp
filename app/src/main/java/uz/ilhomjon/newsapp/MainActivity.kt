package uz.ilhomjon.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.database.entity.AllCategory
import uz.ilhomjon.newsapp.databinding.ActivityMainBinding
import uz.ilhomjon.newsapp.utils.Status
import uz.ilhomjon.newsapp.viewmodel.CategoryNewsViewModel
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import uz.ilhomjon.newsapp.viewmodel.TopHeadlinesViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    @Inject
    lateinit var topHeadlinesViewModel: TopHeadlinesViewModel
    @Inject
    lateinit var categoryNewsViewModel: CategoryNewsViewModel
    @Inject
    lateinit var databaseViewModel: DatabaseViewModel

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        launch(Dispatchers.IO) {
            topHeadlinesViewModel.getStateFlow().collectLatest {
                when (it.status) {
                    Status.SUCCESS->{
//                        Log.d("@@@", "onCreate: ${it.data}")
                    }
                    Status.ERROR->{
//                        Log.d("@@@", "onCreate: ${it.message}")
                    }
                    Status.LOADING->{
//                        Log.d("@@@", "onCreate: ${it.message}")
                    }
                }
            }

            databaseViewModel.addCategory(AllCategory(category_name = "Business"))

            categoryNewsViewModel.getCategoryNews("science", "7c04fcfddd224ed6a591ac49e9abb8f2").collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        Log.d("@@@", "onCreate: ${it.data}")
                    }
                    Status.ERROR->{
                        Log.d("@@@", "onCreate: ${it.message}")
                    }
                    Status.LOADING->{
                        Log.d("@@@", "onCreate: ${it.message}")
                    }
                }
            }

        }

    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}