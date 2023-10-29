package com.example.fortuneoi.Fragments.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fortuneoi.Activity.SearchActivity
import com.example.fortuneoi.Adapter.YahooFinanceService
import com.example.fortuneoi.R
import com.example.fortuneoi.data.FinanceData
import com.example.fortuneoi.data.MarketData
import com.google.android.material.snackbar.Snackbar


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
        presenter.start()
        val editText = view.findViewById<EditText>(R.id.edit_text)

        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = editText.text.toString()
                if (!inputText.isBlank()) {
                    val intent = Intent(activity, SearchActivity::class.java)
                    // Pass any data to the new activity if needed.
                    intent.putExtra("inputText", inputText)
                    startActivity(intent)
                }
                true
            } else {
                false
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews() // Now, bind the views after onCreateView returns the view
    }

    override fun showError(errorMessage: String) {
        Snackbar.make(homeContainer, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun bindMarketSummary(data: MarketData) {
        rvUSMarkets.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvUSMarkets.adapter = MarketSummaryAdapter(data)
    }

    private fun bindViews() {
        homeContainer = requireView().findViewById(R.id.home_container)
        rvUSMarkets = requireView().findViewById(R.id.rvUSMarkets)
        rvTrending = requireView().findViewById(R.id.rvTrending)
    }

    override fun bindTrending(data: FinanceData) {
        rvTrending.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvTrending.adapter = StockAdapter(data)
    }

}