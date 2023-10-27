package com.example.fortuneoi.data

data class TrendingData(
    val finance: TrendingData2
)

data class TrendingData2(
    val result: List<TrendingData3>
)

data class TrendingData3(
    val quotes: List<TrendingData4>
)

data class TrendingData4(
    val symbol: String
)



