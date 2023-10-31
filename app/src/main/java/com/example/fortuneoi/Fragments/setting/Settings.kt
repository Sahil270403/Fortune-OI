package com.example.fortuneoi.Fragments.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.fortuneoi.Activity.loginMain
import com.example.fortuneoi.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class settings : Fragment() { // Correct the class name and inherit from Fragment

    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val logout = view.findViewById<CardView>(R.id.logout_card)

        logout.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // User is signed in with email and password, navigate to the next activity
                val intent = Intent(requireContext(), loginMain::class.java)
                auth.signOut()
                Toast.makeText(requireContext(), "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                requireActivity().finish()
            } else {
                // You should use mGoogleSignInClient instead of undefined googleSignInClient
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    startActivity(Intent(requireContext(), loginMain::class.java))
                    requireActivity().finish()
                }
            }
        }
        logout.setOnClickListener {
            val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
            val currentUser = auth.currentUser

            if (googleSignInAccount != null) {
                // User is signed in with Google Sign-In
                mGoogleSignInClient.signOut().addOnCompleteListener { googleSignOutTask ->
                    if (googleSignOutTask.isSuccessful) {
                        // Google Sign-Out successful
                        val intent = Intent(requireContext(), loginMain::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        // Handle the sign-out failure
                        Toast.makeText(
                            requireContext(),
                            "Failed to sign out from Google",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else if (currentUser != null) {
                // User is signed in with email and password
                auth.signOut() // Sign out the Firebase user
                Toast.makeText(requireContext(), "Logging Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), loginMain::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                // Handle the case where the user is not signed in with Google or Firebase
                Toast.makeText(
                    requireContext(),
                    "No user signed in to log out",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        val profilebtn = view.findViewById<CardView>(R.id.profile_card)
        profilebtn.setOnClickListener {
            val intent  = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
