package com.example.fortuneoi.Activity.search

import com.example.fortuneoi.Adapter.YahooFinanceService

class SearchPresenter (val view: SearchView) {
    val yahooService = YahooFinanceService()

    fun start(parameter: String) {
        getSearchData(parameter)
    }

    fun getSearchData(parameter: String) {
        yahooService.getSearchData(
            parameter,
            successCallback = { data ->
                view.bindSearchData(data)
            },
            failureCallback = { errorMessage ->
                view.showError(errorMessage)
            }
        )
    }
}