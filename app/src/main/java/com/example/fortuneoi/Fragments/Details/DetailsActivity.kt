package com.example.fortuneoi.Fragments.Details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import com.example.fortuneoi.Adapter.YahooFinanceService
import com.example.fortuneoi.R
import com.example.fortuneoi.data.FinanceData
import com.example.fortuneoi.data.StatsData
import com.example.fortuneoi.databinding.ActivityDetailsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity(), DetailsView {

    val presenter = DetailsPresenter(this, yahooService = YahooFinanceService())
    lateinit var detailsContainer: View
    lateinit var stockName: TextView
    lateinit var stockPrice: TextView
    lateinit var stockPercentChange: TextView
    lateinit var favoritesButton: ToggleButton
    lateinit var lineChart: LineChart
    lateinit var marketOpen: TextView
    lateinit var marketClose: TextView
    lateinit var volume: TextView
    lateinit var marketCap: TextView
    lateinit var fiftyTwoWeekLow: TextView
    lateinit var fiftyTwoWeekHigh: TextView
    private val yahooService = YahooFinanceService()

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()

        val symbolID = yahooService.getIntent(intent)

        presenter.start(symbolID)

        val favoriteSet = yahooService.getWatchlistData()

        if (symbolID in favoriteSet) {
            favoritesButton.isChecked = true
        }

        favoritesButton.setOnClickListener {
            if (favoritesButton.isChecked) {
                yahooService.addToWatchlist(symbolID)
            }
            if (!favoritesButton.isChecked) {
                yahooService.removeFromWatchlist(symbolID)
            }
        }

        title = "Details"
    }

    override fun showError(errorMessage: String) {
        Snackbar.make(detailsContainer, errorMessage, Snackbar.LENGTH_LONG).show()

    }

    override fun bindChartData(data: LineDataSet) {

        data.apply {
            color = Color.parseColor("#00d964")
            setDrawCircles(false)
            setDrawHighlightIndicators(true)
            highLightColor = Color.parseColor("#ffffff")
            setDrawValues(false)
            lineWidth = 1.25f
        }

        lineChart.data = LineData(data)

        lineChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            setExtraOffsets(15f, 15F, 30f, 15F)
        }

        /* From what I found online using MPAndroidCharts, you can't set X/Y Axis titles. You can use a textview but
        decided to just format the numbers so they are easier to understand using the two formatter classes I made.
        These formatters are needed because you must pass a float into the entries, then you can format those values
        later on using the valueFormatter. */

        lineChart.axisLeft.apply {
            isEnabled = true
            textColor = Color.parseColor("#ffffff")
            textSize = 15f
            axisLineWidth = 1f
            setDrawGridLines(false)
            valueFormatter = PriceFormatter()
        }

        lineChart.xAxis.apply {
            isEnabled = true
            textColor = Color.parseColor("#ffffff")
            textSize = 15f
            axisLineWidth = 1f
            setDrawGridLines(false)
            valueFormatter = DateFormatter()
            setLabelCount(6, true)
            position = XAxis.XAxisPosition.BOTTOM
        }

        lineChart.invalidate()

    }

    @SuppressLint("SetTextI18n")
    override fun bindStockData(data: FinanceData) {
        val stock = data.quoteResponse.result[0]

        stockName.text = stock.shortName

        if (stock.regularMarketPrice >= 0.01) {
            stockPrice.text = String.format("$%,.2f", stock.regularMarketPrice)

        } else {
            stockPrice.text = String.format("%.6f", stock.regularMarketPrice.toBigDecimal())
        }

        val roundedPercentChange =
            String.format("%.2f", stock.regularMarketChangePercent)

        if (stock.regularMarketChangePercent >= 0) {
            stockPercentChange.setTextColor(Color.parseColor("#00D964"))
            stockPercentChange.text = "+$roundedPercentChange%"
        } else {
            stockPercentChange.setTextColor(Color.parseColor("#FC0000"))
            stockPercentChange.text = "$roundedPercentChange%"
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindStatsData(data: StatsData) {
        val stock = data.quoteSummary.result[0].summaryDetail

        marketCap.text = "Mkt Cap: ${stock.marketCap?.fmt}"
        marketOpen.text = "Open: ${stock.regularMarketOpen?.fmt}"
        marketClose.text = "Close: ${stock.regularMarketPreviousClose?.fmt}"
        volume.text = "Volume: ${stock.volume?.fmt}"
        fiftyTwoWeekHigh.text = "52Wk Hi: ${stock.fiftyTwoWeekHigh?.fmt}"
        fiftyTwoWeekLow.text = "52Wk Lo: ${stock.fiftyTwoWeekLow?.fmt}"

    }

    private fun bindViews() {
        detailsContainer = findViewById(R.id.details_container)
        stockName = findViewById(R.id.stock_name)
        stockPrice = findViewById(R.id.stock_price)
        stockPercentChange = findViewById(R.id.stock_percent_change)
        favoritesButton = findViewById(R.id.favoritesButton)
        lineChart = findViewById(R.id.line_chart)
        marketOpen = findViewById(R.id.open)
        marketClose = findViewById(R.id.close)
        volume = findViewById(R.id.volume)
        marketCap = findViewById(R.id.marketcap)
        fiftyTwoWeekHigh = findViewById(R.id.fiftyHigh)
        fiftyTwoWeekLow = findViewById(R.id.fiftyLow)

    }

}