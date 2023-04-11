package uz.ilhomjon.newsapp.view.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private val binding by lazy { FragmentSecondBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.thirdFragment)
        }

        return binding.root
    }
}