package com.bangkit.farmtrade.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.farmtrade.R
import com.bangkit.farmtrade.databinding.FragmentForecastBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
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
        val forecastViewModel = ViewModelProvider(this)[ForecastViewModel::class.java]

        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        forecastViewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        binding.layoutDate.setEndIconOnClickListener {
            var selection = MaterialDatePicker.todayInUtcMilliseconds()
            if(binding.edTanggal.text.toString().isNotEmpty()){
                try {
                    val dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                        .withZone(ZoneId.of("UTC"))
                    val tempAccessor = dtf.parse(binding.edTanggal.text.toString())
                    val date = LocalDate.from(tempAccessor)
                    if (date != null) {
                        val datetime = date.atStartOfDay()
                        val zdt =datetime.atZone(ZoneId.of("UTC"))
                        selection = zdt.toEpochSecond() * 1000
                        Log.d("from text", selection.toString())
                    }
                } catch (_: Exception) {}
            }
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih tanggal yang diprediksi")
                .setSelection(selection)
                .build()
            datePicker.addOnPositiveButtonClickListener { date ->
                val formatter = SimpleDateFormat("MM/dd/yyyy", Locale("id","ID"))
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = date
                Log.d("millis", date.toString())
                val dateText = formatter.format(calendar.time)
                binding.edTanggal.setText(dateText)
            }
            datePicker.show(childFragmentManager,"datePicker")
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.komoditas_array,
            R.layout.dropdown_item
        )
        binding.edKomoditas.setAdapter(adapter)
        val adapterDaerah = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.daerah_array,
            R.layout.dropdown_item
        )
        binding.edDaerah.setAdapter(adapterDaerah)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}