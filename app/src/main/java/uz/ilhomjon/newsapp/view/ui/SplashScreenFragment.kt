package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentSplashScreenBinding
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment(), CoroutineScope {

    @Inject
    lateinit var databaseViewModel: DatabaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectSplashFragment(this)
        super.onCreate(savedInstanceState)
    }

    private val binding by lazy { FragmentSplashScreenBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        var status: Boolean? = null
        launch {
            val db = databaseViewModel.getAllCategory().collectLatest {
                    Log.d("@splashScreen", "onCreateView: ${it.data}")

                status = it.data?.size != 0
                Log.d("@splashScreen", "onCreateView: $status")
            }

        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (status == true) {
                findNavController().navigate(R.id.mainFragment)
            } else {
                findNavController().navigate(R.id.firstFragment)
            }
        }, 3000)


        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}