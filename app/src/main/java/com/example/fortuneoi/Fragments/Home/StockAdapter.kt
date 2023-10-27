package com.example.fortuneoi.Fragments.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fortuneoi.R
import com.example.fortuneoi.data.FinanceData

class StockAdapter(private val data: FinanceData) :
    RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stock_card_rectangle, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = data.quoteResponse.result[position]

        if(stock.displayName != null) {
            holder.tvName.text = stock.displayName
        } else {
            holder.tvName.text = stock.shortName
        }

        if(stock.regularMarketPrice >= 0.01) {
            holder.tvPrice.text = String.format("%,.2f", stock.regularMarketPrice)
        } else{
            holder.tvPrice.text = String.format("%.6f", stock.regularMarketPrice.toBigDecimal())
        }

        val roundedPercentChange =
            String.format("%.2f", stock.regularMarketChangePercent)

        if (stock.regularMarketChangePercent >= 0) {
            holder.tvPercentChange.setTextColor(Color.parseColor("#00D964"))
            holder.tvPercentChange.text = "+$roundedPercentChange%"
        } else {
            holder.tvPercentChange.setTextColor(Color.parseColor("#FC0000"))
            holder.tvPercentChange.text = "$roundedPercentChange%"
        }

//        holder.card.setOnClickListener {
//            val context = holder.card.context
//            val intent = Intent(context, DetailsActivity::class.java)
//            intent.putExtra("id", stock.symbol.toString());
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return data.quoteResponse.result.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.stock_card_rectangle)
        val tvName: TextView = card.findViewById(R.id.name)
        val tvPrice: TextView = card.findViewById(R.id.price)
        val tvPercentChange: TextView = card.findViewById(R.id.percentChange)
    }
}