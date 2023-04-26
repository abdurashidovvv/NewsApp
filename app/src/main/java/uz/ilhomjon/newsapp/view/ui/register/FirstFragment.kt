package uz.ilhomjon.newsapp.view.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentFirstBinding
import uz.ilhomjon.newsapp.view.adapters.TravelLocationsAdapter
import kotlin.math.abs

class FirstFragment : Fragment() {

    private val binding by lazy { FragmentFirstBinding.inflate(layoutInflater) }
    private lateinit var list: ArrayList<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        list = ArrayList()
        list.add(R.drawable.splash1)
        list.add(R.drawable.splash3)
        list.add(R.drawable.splash2)

        binding.locationsViewPager.adapter = TravelLocationsAdapter(list)
        binding.apply {
            locationsViewPager.clipToPadding = false
            locationsViewPager.clipChildren = false
            locationsViewPager.offscreenPageLimit = 3
            locationsViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r: Float = 1 - abs(position)
                page.scaleY = 0.95f + r * 0.05f
            }
            locationsViewPager.setPageTransformer(compositePageTransformer)


            binding.nextBtn.setOnClickListener {
                findNavController().navigate(R.id.secondFragment)
            }
            return binding.root
        }
    }
}