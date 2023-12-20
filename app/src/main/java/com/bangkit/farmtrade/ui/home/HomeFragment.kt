package com.bangkit.farmtrade.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.farmtrade.R
import com.bangkit.farmtrade.databinding.FragmentHomeBinding
import com.bangkit.farmtrade.ui.adapters.ImageAdapter
import com.bangkit.farmtrade.ui.adapters.ImageItem
import com.bangkit.farmtrade.ui.adapters.ProductAdapter
import com.bangkit.farmtrade.ui.adapters.ProductItem
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

            val productAdapter = ProductAdapter()
//            productAdapter.setOnItemClickCallback(object: ProductAdapter.OnItemClickCallback{
//                override fun onItemClicked(product: ProductItem) {
//
//                }
//            })
            rvProducts.adapter = productAdapter
            rvProducts.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val productList = listOf(
                ProductItem(
                    "v1",
                    "Carrot",
                    15000,
                    "Fresh and crunchy carrots",
                    "https://fruit-vegi-list-api.cyclic.app/images/carrot.jpg",
                    "Farm Fresh Produce",
                    "+1234567890"
                ),
                ProductItem(
                    "v2",
                    "Broccoli",
                    12000,
                    "Nutrient-rich broccoli florets",
                    "https://fruit-vegi-list-api.cyclic.app/images/beetroot.jpg",
                    "Green Gardens",
                    "+9876543210"
                ),
                ProductItem(
                    "v3",
                    "Spinach",
                    18000,
                    "Fresh and nutrient-packed spinach leaves",
                    "https://fruit-vegi-list-api.cyclic.app/images/cauli-flower.jpg",
                    "Organic Greens Farm",
                    "+9876543210"
                ),
                ProductItem(
                    "v4",
                    "Bell Pepper",
                    20000,
                    "Colorful bell peppers for cooking",
                    "https://fruit-vegi-list-api.cyclic.app/images/tomatoes.jpeg",
                    "Rainbow Harvest",
                    "+1234567890"
                ),
                ProductItem(
                    "f1",
                    "Apple",
                    13000,
                    "Sweet and juicy red apples",
                    "https://fruit-vegi-list-api.cyclic.app/images/apple.jpg",
                    "Orchard Delights",
                    "+1112233445"
                ),
                ProductItem(
                    "f2",
                    "Banana",
                    15000,
                    "Ripe yellow bananas",
                    "https://fruit-vegi-list-api.cyclic.app/images/banana.jpg",
                    "Tropical Fruits Farm",
                    "+5544332211"
                ),
                ProductItem(
                    "f3",
                    "Grapes",
                    17000,
                    "Juicy and sweet grapes bunch",
                    "https://fruit-vegi-list-api.cyclic.app/images/graphes.png",
                    "Vineyard Delights",
                    "+9876543210"
                ),
                ProductItem(
                    "f4",
                    "Mango",
                    19000,
                    "Ripe and delicious mangoes",
                    "https://fruit-vegi-list-api.cyclic.app/images/mango.jpg",
                    "Tropical Fruits Farm",
                    "+1112233445"
                )
            )
            productAdapter.submitList(productList)
        }
        val root: View = binding.root
        return root
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.lokasi_array,
            R.layout.dropdown_item
        )
        binding.edLokasi.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}