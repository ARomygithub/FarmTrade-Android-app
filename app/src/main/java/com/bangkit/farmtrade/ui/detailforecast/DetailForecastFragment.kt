package com.bangkit.farmtrade.ui.detailforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.farmtrade.R
import com.bangkit.farmtrade.data.remote.response.ForecastResponse
import com.bangkit.farmtrade.databinding.FragmentDetailForecastBinding
import com.bangkit.farmtrade.ui.chart.DateAxisFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailForecastFragment: Fragment(), OnChartValueSelectedListener {
    private var _binding: FragmentDetailForecastBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailForecastViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailForecastBinding.inflate(inflater, container, false)
        detailViewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        with(binding) {
            nameKomoditas.text = DetailForecastFragmentArgs.fromBundle(arguments as Bundle).komoditas
            daerahKomoditas.text = DetailForecastFragmentArgs.fromBundle(arguments as Bundle).daerah
            tanggalKomoditas.text = DetailForecastFragmentArgs.fromBundle(arguments as Bundle).dateToPredict
            detailViewModel.forecast.observe(viewLifecycleOwner) {forecast ->
                setChart(forecast)
                binding.priceKomoditas.setText(forecast.futureForecast.last().toString())
                binding.imageKomoditas.setImageResource(R.drawable.red_onion)
            }
            detailViewModel.getForecast(
                nameKomoditas.text.toString(),
                daerahKomoditas.text.toString(),
                tanggalKomoditas.text.toString()
            )
        }
        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun setChart(response: ForecastResponse) {
        with(binding) {
            chart.setOnChartValueSelectedListener(this@DetailForecastFragment)
            chart.setDrawGridBackground(false)
            chart.description.isEnabled = false
            chart.setDrawBorders(false)
            chart.axisRight.isEnabled = false
            chart.axisLeft.setDrawGridLines(true)
            chart.axisLeft.setDrawAxisLine(true)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.setDrawAxisLine(true)
            chart.xAxis.setDrawGridLines(false)
            chart.xAxis.valueFormatter = DateAxisFormatter()
            chart.xAxis.isGranularityEnabled = true
            // enable touch gestures
            chart.setTouchEnabled(true)

            // enable scaling and dragging
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)

            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(false)

            val entries = ArrayList<Entry>()
            val forecastVal = response.futureForecast
            for(i in forecastVal.indices) {
                entries.add(Entry((i+1).toFloat(), forecastVal[i].toFloat()))
            }
            val lds = LineDataSet(entries, nameKomoditas.text.toString())
            lds.setDrawCircles(true)
            lds.setDrawValues(true)
            lds.lineWidth = 2.5f
            lds.circleRadius = 4f
            lds.enableDashedLine(10f, 10f, 0f)
//            lds.setColors(ColorTemplate.LIBERTY_COLORS, requireContext())
//            lds.setCircleColors(ColorTemplate.LIBERTY_COLORS, requireContext())
//            lds.color = R.color.md_theme_light_primary
//            lds.setCircleColor(R.color.md_theme_light_primary)
            val arrLds = ArrayList<ILineDataSet>()
            arrLds.add(lds)
            chart.data = LineData(arrLds)
            chart.invalidate()
            chart.animateX(1500)
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

    override fun onNothingSelected() {
    }
}