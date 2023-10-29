package com.example.fortuneoi.Fragments.Home

import android.app.Application
import android.content.Context

class StockApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}

