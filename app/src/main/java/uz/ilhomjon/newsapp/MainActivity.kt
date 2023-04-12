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

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}