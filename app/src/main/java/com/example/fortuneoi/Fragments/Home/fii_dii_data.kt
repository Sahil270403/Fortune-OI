package com.example.fortuneoi.Fragments.Home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fortuneoi.Adapter.YahooFinanceService
import com.example.fortuneoi.R
import com.example.fortuneoi.data.FinanceData
import com.example.fortuneoi.data.MarketData
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar


class fii_dii_data : Fragment(), HomeView {
    val presenter = HomePresenter(this, yahooService = YahooFinanceService())
    lateinit var rvUSMarkets: RecyclerView
    lateinit var rvTrending: RecyclerView
    lateinit var homeContainer: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fii_dii_data, container, false)
        // Inflate the layout for this fragment
        setUpTitle()
//        setUpNav()
//        bindViews()
        presenter.start()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews() // Now, bind the views after onCreateView returns the view
    }

//    private fun setUpNav() {
//        // This helps make the activity switching/nav bar less abrupt, didn't get a chance to add Fragments yet
//        overridePendingTransition(
//            androidx.transition.R.anim.abc_fade_in,
//            androidx.transition.R.anim.abc_fade_out
//        )
//
//        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
//        bottomNavigation.selectedItemId = R.id.ic_home
//        bottomNavigation.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.ic_home -> startActivity(Intent(this, HomeActivity::class.java))
//                R.id.ic_search -> startActivity(Intent(this, SearchActivity::class.java))
//                R.id.ic_watchlist -> startActivity(Intent(this, WatchlistActivity::class.java))
//            }
//            true
//        }
//    }

    @SuppressLint("SimpleDateFormat")
    fun setUpTitle() {
        val calender = Calendar.getInstance()
        val currentMonth = SimpleDateFormat("MMMM").format(calender.time)
        val currentDay = SimpleDateFormat("d").format(calender.time)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Home"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "$currentMonth $currentDay"
    }


    override fun showError(errorMessage: String) {
        Snackbar.make(homeContainer, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun bindMarketSummary(data: MarketData) {
//        rvUSMarkets.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        rvUSMarkets.adapter = MarketSummaryAdapter(data)
    }

    private fun bindViews() {
        homeContainer = requireView().findViewById(R.id.home_container)
        rvUSMarkets = requireView().findViewById(R.id.rvUSMarkets)
        rvTrending = requireView().findViewById(R.id.rvTrending)
    }

    override fun bindTrending(data: FinanceData) {
//        rvTrending.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        rvTrending.adapter = StockAdapter(data)
    }

}