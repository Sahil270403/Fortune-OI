package com.example.fortuneoi.Fragments.Home

import com.example.fortuneoi.data.FinanceData
import com.example.fortuneoi.data.MarketData

interface HomeView {
    fun showError(errorMessage: String)
    fun bindMarketSummary(data: MarketData)
    fun bindTrending(data: FinanceData)
}