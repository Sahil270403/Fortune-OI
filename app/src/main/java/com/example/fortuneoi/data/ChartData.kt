package com.example.fortuneoi.data

data class ChartData(
    val chart: ChartData2
)

data class ChartData2(
    val result: List<ChartData3>
)

data class ChartData3(
    val regularMarketPrice: Float,
    val chartPreviousClose: Float,
    val timestamp: List<Int?>,
    val indicators: ChartData4
)

data class ChartData4(
    val quote: List<ChartData5>
)

data class ChartData5(
    val close: List<Float?>
)