package com.example.fortuneoi.data

data class StatsData(
    val quoteSummary: StatsData2
)

data class StatsData2(
    val result: List<StatsData10>
)
data class StatsData10(
    val summaryDetail: StatsData3
)

data class StatsData3(
    val regularMarketOpen: StatsData8?,
    val regularMarketPreviousClose: StatsData9?,
    val volume: StatsData7?,
    val marketCap: StatsData4?,
    val fiftyTwoWeekLow: StatsData5?,
    val fiftyTwoWeekHigh: StatsData6?,
)

data class StatsData4(
    val fmt: String? = "N/A"
)

data class StatsData5(
    val fmt: String? = "N/A"
)

data class StatsData6(
    val fmt: String? = "N/A"
)

data class StatsData7(
    val fmt: String? = "N/A"
)

data class StatsData8(
    val fmt: String? = "N/A"
)

data class StatsData9(
    val fmt: String? = "N/A"
)
