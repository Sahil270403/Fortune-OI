package com.example.fortuneoi.Fragments.Feed

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fortuneoi.Adapter.NewsItemClicked
import com.example.fortuneoi.Adapter.NewsListAdapter
import com.example.fortuneoi.data.News
import com.example.fortuneoi.databinding.FragmentFeedBinding

class feed : Fragment() {
    private lateinit var mAdapter: NewsListAdapter
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerview1
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter here (create your NewsListAdapter)
        mAdapter = NewsListAdapter(requireContext(), object : NewsItemClicked {
            override fun onitemClicked(item: News) {
                // Handle item click here if needed
                // Open a Chrome Custom Tab or perform any other action here
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(requireContext(), Uri.parse(item.url))
            }
        })


        recyclerView.adapter = mAdapter

        fetchData(1) // Call the function to fetch data
    }

    private fun fetchData(page: Int) {
        val pageSize = 100 // You can adjust this based on your needs
        val queue = Volley.newRequestQueue(requireContext())

        val url =
            "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=c01c56bb90e64787a57ad76a4dbd2d93" +
                    "&pageSize=$pageSize" +
                    "&page=$page" // Specify the current page

        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                // Handle the response and add articles to your list
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                // Update your adapter with the new data
                mAdapter.updateNews(newsArray)

                // Fetch the next page if needed
                if (newsJsonArray.length() == pageSize) {
                    // If the current page is not the last one, fetch the next page
                    fetchData(page + 1)
                }
            },
            Response.ErrorListener { error ->
                // Handle errors here
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

        queue.add(getRequest)
    }
}

