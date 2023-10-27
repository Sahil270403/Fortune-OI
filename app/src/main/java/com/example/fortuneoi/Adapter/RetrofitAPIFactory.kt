package com.example.fortuneoi.Adapter

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPIFactory {

    private val apiKey = "jEZLZ5QzZc5B29pzxUhwR1TZrcg15dtn2KBcvYaP"

    fun getYahooFinanceAPI(): YahooFinanceAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", apiKey)
                chain.proceed(request.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://yfapi.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(YahooFinanceAPI::class.java)
    }
}
