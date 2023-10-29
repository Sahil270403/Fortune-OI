package com.example.fortuneoi.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fortuneoi.Activity.search.SearchAdapter
import com.example.fortuneoi.Activity.search.SearchPresenter
import com.example.fortuneoi.Activity.search.SearchView
import com.example.fortuneoi.R
import com.example.fortuneoi.data.SearchData
import com.example.fortuneoi.databinding.ActivitySearchAactivityBinding
import com.google.android.material.snackbar.Snackbar

class SearchActivity : AppCompatActivity(), SearchView {

    private lateinit var binding : ActivitySearchAactivityBinding
    val presenter = SearchPresenter(this)
    lateinit var searchContainer: View
    lateinit var searchBar: EditText
    lateinit var rvSearch: RecyclerView
    lateinit var searchButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchAactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Retrieve the string extra that was passed
        val inputText = intent.getStringExtra("inputText")

        // Now, you can use the inputText as needed in this activity
        val editText = binding.searchBar as EditText // Replace with the actual EditText in your layout

        if (!TextUtils.isEmpty(inputText)) {
            val editableText: Editable = SpannableStringBuilder.valueOf(inputText)
            editText.text = editableText
            presenter.start(inputText.toString())
        }

        bindViews()
//        setUpNav()

//        searchButton.setOnClickListener {
//            val parameter = searchBar.text.toString()
//            if(parameter.isNotEmpty()) {
//                presenter.start(parameter)
//            }
//        }
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val parameter = searchBar.text.toString()
                if(parameter.isNotEmpty()) {
                    presenter.start(parameter)
                }
                true
            } else {
                false
            }
        }
        title = "Search"
    }

//    fun setUpNav(){
//        overridePendingTransition(androidx.transition.R.anim.abc_fade_in, androidx.transition.R.anim.abc_fade_out)
//
//        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
//        bottomNavigation.selectedItemId = R.id.ic_search
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
        Snackbar.make(searchContainer, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun bindSearchData(data: SearchData) {
        rvSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSearch.adapter = SearchAdapter(data)
    }

    private fun bindViews() {
        searchContainer = findViewById(R.id.search_container)
        searchBar = findViewById(R.id.search_bar)
//        searchButton = findViewById(R.id.search_button)
        rvSearch = findViewById(R.id.rvSearch)
    }

}