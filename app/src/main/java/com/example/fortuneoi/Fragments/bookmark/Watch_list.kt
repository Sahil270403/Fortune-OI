package com.example.fortuneoi.Fragments.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fortuneoi.Adapter.YahooFinanceService
import com.example.fortuneoi.Fragments.Home.StockAdapter
import com.example.fortuneoi.R
import com.example.fortuneoi.data.FinanceData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


class watch_list : Fragment(), WatchlistView {

    val presenter = WatchlistPresenter(this, yahooService = YahooFinanceService())
    lateinit var rvWatchlist: RecyclerView
    lateinit var watchlistContainer: View
    lateinit var tvWatchlistError: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_watch_list, container, false)

//        bindViews()
//        setUpNav()
        presenter.start()

//        title = "Watchlist"
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews() // Now, bind the views after onCreateView returns the view
    }

//    fun setUpNav() {
//        overridePendingTransition(
//            androidx.transition.R.anim.abc_fade_in,
//            androidx.transition.R.anim.abc_fade_out
//        )
//
//        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
//        bottomNavigation.selectedItemId = R.id.ic_watchlist
//        bottomNavigation.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.ic_home -> startActivity(Intent(this, HomeActivity::class.java))
//                R.id.ic_search -> startActivity(Intent(this, SearchActivity::class.java))
//                R.id.ic_watchlist -> startActivity(Intent(this, WatchlistActivity::class.java))
//            }
//            true
//        }
//    }

    override fun showError(errorMessage: String) {
        Snackbar.make(watchlistContainer, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun showEmptyWatchlistError() {
        tvWatchlistError.visibility = View.VISIBLE
    }

    override fun bindWatchlist(data: FinanceData) {
        rvWatchlist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvWatchlist.adapter = StockAdapter(data)
    }

    private fun bindViews() {
        watchlistContainer = requireView().findViewById(R.id.watchlist_container)
        rvWatchlist = requireView().findViewById(R.id.rvWatchlist)
        tvWatchlistError = requireView().findViewById(R.id.watchlistError)
    }
}