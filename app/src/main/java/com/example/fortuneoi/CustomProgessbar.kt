package com.example.fortuneoi

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

class CustomProgressBar(private val context: Context) {
    private var progressDialog: AlertDialog? = null

    // Function to show the custom progress dialog
    fun show() {
        if (progressDialog == null) {
            val builder = AlertDialog.Builder(context)
            builder.setView(LayoutInflater.from(context).inflate(R.layout.progress_alert_dialog, null))
            builder.setCancelable(false)

            progressDialog = builder.create()
            progressDialog?.show()
        }
    }

    // Function to dismiss the custom progress dialog
    fun dismiss() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}



