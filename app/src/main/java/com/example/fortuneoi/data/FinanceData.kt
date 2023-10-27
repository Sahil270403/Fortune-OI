package com.example.fortuneoi.data

data class FinanceData(
    val quoteResponse: FinanceData2
)

data class FinanceData2(
    val result: List<FinanceData3>
)

data class FinanceData3(
    val shortName: String,
    val displayName: String?,
    val regularMarketPrice: Float,
    val regularMarketChangePercent: Float,
    val fullExchangeName: String?,
    val symbol: String? = "-",
    val fiftyTwoWeekHigh: Float,
    val fiftyTwoWeekLow: Float,
    val marketCap: Float,
    val typeDisp: String?
)
