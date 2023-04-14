package uz.ilhomjon.newsapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentLanguageBinding

class InfoFragment : Fragment() {

    private val binding by lazy { FragmentLanguageBinding.inflate(layoutInflater)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        return binding.root
    }
}