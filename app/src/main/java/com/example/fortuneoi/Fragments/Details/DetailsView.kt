package com.example.fortuneoi.Fragments.Details

import com.example.fortuneoi.data.FinanceData
import com.example.fortuneoi.data.StatsData
import com.github.mikephil.charting.data.LineDataSet

interface DetailsView {
    fun showError(errorMessage: String)
    fun bindChartData(data: LineDataSet)
    fun bindStockData(data: FinanceData)
    fun bindStatsData(data: StatsData)
}
