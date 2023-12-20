package com.bangkit.farmtrade.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.farmtrade.R
import com.bangkit.farmtrade.databinding.FragmentHomeBinding
import com.bangkit.farmtrade.ui.adapters.ImageAdapter
import com.bangkit.farmtrade.ui.adapters.ImageItem
import com.google.android.material.carousel.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        with(binding) {
            val imageAdapter = ImageAdapter()
            carouselRv.adapter = imageAdapter
            carouselRv.layoutManager = CarouselLayoutManager()
            val imageList = listOf<ImageItem>(
                ImageItem(
                    UUID.randomUUID().toString(),
                    R.drawable.promo
                ),
                ImageItem(
                    UUID.randomUUID().toString(),
                    R.drawable.promo
                ),
                ImageItem(
                    UUID.randomUUID().toString(),
                    R.drawable.promo
                )
            )
            imageAdapter.submitList(imageList)
        }
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}