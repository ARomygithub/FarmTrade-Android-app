package com.bangkit.farmtrade.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(

	@field:SerializedName("date_forecast")
	val dateForecast: List<String>,

	@field:SerializedName("future_forecast")
	val futureForecast: List<Double>
)
