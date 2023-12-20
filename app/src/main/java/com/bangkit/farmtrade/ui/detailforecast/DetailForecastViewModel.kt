package com.bangkit.farmtrade.ui.detailforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.farmtrade.data.remote.response.ForecastResponse

class DetailForecastViewModel: ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getForecast(komoditas: String, region: String, dateToPredict: String) : ForecastResponse {
        // TODO: get forecast from API
        _isLoading.value = true
        val forecastResponse = ForecastResponse(
            dateForecast = listOf("2021-06-01", "2021-06-02", "2021-06-03", "2021-06-04", "2021-06-05"),
            futureForecast = listOf(100.0, 200.0, 300.0, 400.0, 500.0)
        )
        _isLoading.value = false
        return forecastResponse
    }
}