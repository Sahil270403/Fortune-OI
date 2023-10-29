package com.example.fortuneoi.Activity.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fortuneoi.Fragments.Details.DetailsActivity
import com.example.fortuneoi.R
import com.example.fortuneoi.data.SearchData

class SearchAdapter (private val data: SearchData) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stock_card_search, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = data.ResultSet.Result[position]

        holder.tvName.text = stock.name
        holder.tvSymbol.text = stock.symbol

        holder.card.setOnClickListener {
            val context = holder.card.context
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("id", stock.symbol.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.ResultSet.Result.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.stock_card_search)
        val tvName: TextView = card.findViewById(R.id.name)
        val tvSymbol: TextView = card.findViewById(R.id.symbol)
    }
}