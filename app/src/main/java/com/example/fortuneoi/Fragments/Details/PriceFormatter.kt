package com.example.fortuneoi.Fragments.Details

import android.annotation.SuppressLint
import android.icu.text.CompactDecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Locale

class PriceFormatter : IndexAxisValueFormatter() {

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
        val compactDecimal: CompactDecimalFormat = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        return "$${compactDecimal.format(value)}"
    }
}
