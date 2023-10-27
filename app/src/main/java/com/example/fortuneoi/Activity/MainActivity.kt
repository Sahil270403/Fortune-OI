package com.example.fortuneoi.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.fortuneoi.R
import com.example.fortuneoi.databinding.ActivityMainBinding
import com.example.fortuneoi.Fragments.Home.fii_dii_data
import com.example.fortuneoi.Fragments.Feed.feed
import com.example.fortuneoi.Fragments.setting.settings
import com.example.fortuneoi.Fragments.bookmark.watch_list
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(feed())
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    loadFragment(feed())
                    true
                }

                R.id.watchlist -> {
                    loadFragment(watch_list())
                    true
                }

                R.id.fii_dii -> {
                    loadFragment(fii_dii_data())
                    true
                }

                R.id.settings -> {
                    loadFragment(settings())
                    true
                }

                else -> {
                    // Handle the case when an unknown item is selected.
                    // You can show an error message or perform some other action.
                    false // Return false to indicate that the item selection is not handled.
                }
            }
        }

    }
        private fun loadFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
        }
}