package com.bangkit.farmtrade.ui.chart

import android.annotation.SuppressLint
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class DateAxisFormatter: ValueFormatter() {
    @SuppressLint("SimpleDateFormat")
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        try {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val dateStart = formatter.parse("11/01/2024")
            val ldt = LocalDateTime.ofInstant(dateStart!!.toInstant(), ZoneId.systemDefault()).plusDays(value.toLong())
            val zdt = ldt.atZone(ZoneId.systemDefault())
            return formatter.format(Date.from(zdt.toInstant()))
        } catch (_: Exception) { }
        return value.toString()
    }
}