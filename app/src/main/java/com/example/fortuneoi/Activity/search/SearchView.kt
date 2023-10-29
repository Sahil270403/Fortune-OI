package com.example.fortuneoi.Activity.search

import com.example.fortuneoi.data.SearchData

interface SearchView {
    fun showError(errorMessage: String)
    fun bindSearchData(data: SearchData)
}
