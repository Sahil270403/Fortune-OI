package com.example.fortuneoi.Fragments.Details

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat

class DateFormatter : IndexAxisValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String? {
        val results = value.toLong() * 1000
        return SimpleDateFormat("yyyy").format(results)
    }
}