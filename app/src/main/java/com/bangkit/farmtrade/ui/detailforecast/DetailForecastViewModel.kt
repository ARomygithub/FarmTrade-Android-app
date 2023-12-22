package com.bangkit.farmtrade.ui.detailforecast

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.farmtrade.data.remote.request.ForecastRequest
import com.bangkit.farmtrade.data.remote.response.ForecastResponse
import com.bangkit.farmtrade.data.remote.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DetailForecastViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading
    private var _forecast = MutableLiveData<ForecastResponse>()
    val forecast : LiveData<ForecastResponse> = _forecast
    @SuppressLint("SimpleDateFormat")
    fun getForecast(komoditas: String, region: String, dateToPredict: String) {
        _isLoading.value = true
        var daysNumber = 2
        try {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val dateStart = formatter.parse("11/01/2024")
            val ldtStart = LocalDateTime.ofInstant(dateStart!!.toInstant(), ZoneId.systemDefault())
            val zdtStart = ldtStart.atZone(ZoneId.systemDefault())
            val datePredict = formatter.parse(dateToPredict)
            val ldtPredict = LocalDateTime.ofInstant(datePredict!!.toInstant(), ZoneId.systemDefault())
            val zdtPredict = ldtPredict.atZone(ZoneId.systemDefault())
            daysNumber = ChronoUnit.DAYS.between(zdtStart.toLocalDate(), zdtPredict.toLocalDate()).toInt()
        } catch (_ : Exception) {}
        val forecastRequest = ForecastRequest(
            daysNumber = daysNumber,
            region = region,
            komoditas = komoditas
        )
//        viewModelScope.launch {
//            try {
//                val response = apiService.getForecast(forecastRequest)
//                _forecast.value = response
//            } catch (e: HttpException) {
//                val response = ForecastResponse(ArrayList(), ArrayList(), e.message())
//                _forecast.value= response
//            }
//        }
        val forecastResponse = ForecastResponse(
            dateForecast = listOf("2021-06-01", "2021-06-02", "2021-06-03", "2021-06-04", "2021-06-05"),
            futureForecast = listOf(100.0, 200.0, 300.0, 400.0, 500.0)
        )
        _forecast.value = forecastResponse
        _isLoading.value = false
    }
}