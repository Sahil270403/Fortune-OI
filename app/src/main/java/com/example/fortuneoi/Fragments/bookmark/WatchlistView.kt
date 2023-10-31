package com.example.fortuneoi.Fragments.bookmark

import com.example.fortuneoi.data.FinanceData

interface WatchlistView {
    fun showError(errorMessage: String)
    fun bindWatchlist(data: FinanceData)
    fun showEmptyWatchlistError()
}
