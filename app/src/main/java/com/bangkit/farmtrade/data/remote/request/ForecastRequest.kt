package com.bangkit.farmtrade.data.remote.request

import com.google.gson.annotations.SerializedName

data class ForecastRequest (
    @field:SerializedName("hari")
    val daysNumber: Int,
    @field:SerializedName("daerah")
    val region: String,
    @field:SerializedName("makanan")
    val komoditas: String
)