package com.example.fortuneoi.Adapter

import com.example.fortuneoi.data.ChartData
import com.example.fortuneoi.data.FinanceData
import com.example.fortuneoi.data.MarketData
import com.example.fortuneoi.data.SearchData
import com.example.fortuneoi.data.StatsData
import com.example.fortuneoi.data.TrendingData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface YahooFinanceAPI {

    // OkHTTP Interceptor is located in RetrofitAPIFactory file, along with the keys - change keys there if response fails.

    @GET("v6/finance/quote?region=US&lang=en&")
    fun getStockDetails(@Query("symbols")symbols: String): Call<FinanceData>

    @Headers("accept: application/json")
    @GET("v1/finance/trending/US")
    fun getTrendingDetails(): Call<TrendingData>

    @GET("v8/finance/chart/{symbol}?range=max&region=US&interval=1mo&lang=en&events=div%2Csplit")
    fun getChartDetails(@Path("symbol")symbol: String): Call<ChartData>

    @GET("v6/finance/autocomplete?region=US&lang=en&")
    fun getSearchDetails(@Query("query")symbol: String): Call<SearchData>

    @GET("v6/finance/quote/marketSummary")
    fun getMarketSummaryDetails(): Call<MarketData>

    @GET("v11/finance/quoteSummary/{symbol}?lang=en&region=US&modules=summaryDetail")
    fun getStockStatsDetails(@Path("symbol")symbol: String): Call<StatsData>


}