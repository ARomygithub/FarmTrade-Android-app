package com.bangkit.farmtrade.ui.detailproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.farmtrade.databinding.FragmentDetailProductBinding
import com.bumptech.glide.Glide

class DetailProductFragment: Fragment() {
    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        with(binding) {
            detailTitle.text = DetailProductFragmentArgs.fromBundle(arguments as Bundle).title
            detailProducer.text = DetailProductFragmentArgs.fromBundle(arguments as Bundle).producer
            val image = DetailProductFragmentArgs.fromBundle(arguments as Bundle).image
            Glide.with(requireContext()).load(image).into(detailImage)
            detailDeskripsi.text = DetailProductFragmentArgs.fromBundle(arguments as Bundle).description
            priceAfter.text = DetailProductFragmentArgs.fromBundle(arguments as Bundle).priceAfter
            priceBefore.text = DetailProductFragmentArgs.fromBundle(arguments as Bundle).priceBefore
            priceBefore.paintFlags = priceBefore.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            val phoneNumber = DetailProductFragmentArgs.fromBundle(arguments as Bundle).phoneNumber
            val wa = "https://wa.me/$phoneNumber"
            btnBuy.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(wa)
                startActivity(i)
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }
}