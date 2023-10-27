package com.example.fortuneoi.data

data class MarketData(
    val marketSummaryResponse: MarketData2
)

data class MarketData2(
    val result: List<MarketData3>
)

data class MarketData3(
    val shortName: String,
    val symbol: String,
    val regularMarketChangePercent: MarketData4,
    val regularMarketPrice: MarketData5
)

data class MarketData4(
    val raw: Float
)

data class MarketData5(
    val raw: Float
)
