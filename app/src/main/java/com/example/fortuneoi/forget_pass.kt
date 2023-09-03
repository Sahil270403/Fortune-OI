package com.example.fortuneoi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fortuneoi.databinding.ActivityForgetPassBinding
import com.google.firebase.auth.FirebaseAuth

class forget_pass : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPassBinding
    private lateinit var btnReset: Button
    private lateinit var edtEmail: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var strEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnReset = binding.button7
        edtEmail = binding.emailEt

        mAuth = FirebaseAuth.getInstance()

        // Reset Button Listener
        btnReset.setOnClickListener {
            strEmail = edtEmail.text.toString().trim()
            if (!TextUtils.isEmpty(strEmail)) {
                resetPassword()
            } else {
                edtEmail.error = "Email field can't be empty"
            }
        }

        // Back Button Code

    }

    private fun resetPassword() {
        btnReset.visibility = View.INVISIBLE

        mAuth.sendPasswordResetEmail(strEmail)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Reset Password link has been sent to your registered Email",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, loginMain::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error :- ${e.message}", Toast.LENGTH_SHORT).show()
                btnReset.visibility = View.VISIBLE
            }
    }
}