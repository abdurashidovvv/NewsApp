package uz.ilhomjon.newsapp.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.ilhomjon.newsapp.App
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentSplashScreenBinding
import uz.ilhomjon.newsapp.viewmodel.DatabaseViewModel
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

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

        val db = databaseViewModel.getAllCategory()
        var status: Boolean? = null
        status = db.value.data != null

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (status){
                findNavController().navigate(R.id.mainFragment)
            }else{
                findNavController().navigate(R.id.firstFragment)
            }
        }, 3000)


        return binding.root
    }
}