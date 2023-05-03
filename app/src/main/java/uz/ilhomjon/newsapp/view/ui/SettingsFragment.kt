package uz.ilhomjon.newsapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import uz.ilhomjon.newsapp.R
import uz.ilhomjon.newsapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding.lang.setOnClickListener {
            findNavController().navigate(R.id.languageFragment)
        }


        // Listen for changes to the switch state
        binding.darkSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Enable or disable dark mode based on the switch state
            AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
            // Recreate the activity to apply the new night mode
        }
        return binding.root
    }
}