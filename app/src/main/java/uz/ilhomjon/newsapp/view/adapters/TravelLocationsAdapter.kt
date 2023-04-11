package uz.ilhomjon.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import uz.ilhomjon.newsapp.databinding.ItemContainerLocationBinding

class TravelLocationsAdapter(private val list: ArrayList<Int>) :
    RecyclerView.Adapter<TravelLocationsAdapter.Vh>() {

    inner class Vh(private val binding: ItemContainerLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(image: Int) {

            binding.apply {
                kbvLocation.setImageResource(image)
                val interpolator = AccelerateDecelerateInterpolator()

                // It is used to change the duration and
                // the interpolator of transitions

                // It is used to change the duration and
                // the interpolator of transitions
                val generator = RandomTransitionGenerator(2000, interpolator)
                kbvLocation.setTransitionGenerator(generator)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemContainerLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}