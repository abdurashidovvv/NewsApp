package uz.ilhomjon.newsapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        binding.myBottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment()
                    replaceFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.category -> {
                    val fragment = CategoryFragment()
                    replaceFragment(fragment)
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.saved -> {
                    val fragment = SavedFragment()
                    replaceFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.info -> {
                    val fragment = SettingsFragment()
                    replaceFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
        val fragment = HomeFragment()
        replaceFragment(fragment)

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
}