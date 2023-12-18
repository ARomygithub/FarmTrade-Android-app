package com.bangkit.farmtrade.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.farmtrade.R
import com.bangkit.farmtrade.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val forecastViewModel =
            ViewModelProvider(this).get(ForecastViewModel::class.java)

        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.komoditas_array,
            R.layout.dropdown_item
        )
        binding.edKomoditas.setAdapter(adapter)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}