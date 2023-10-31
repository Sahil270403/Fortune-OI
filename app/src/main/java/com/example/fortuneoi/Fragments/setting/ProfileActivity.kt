package com.example.fortuneoi.Fragments.setting

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.fortuneoi.R
import com.example.fortuneoi.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

class ProfileActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityProfileBinding
    private lateinit var calendar: Calendar

    private val PICK_IMAGE_REQUEST = 1
    private var profileImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().getReference("admin_users")

        val genderSpinner = binding.gender1
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,  // Create a string array resource with gender options
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        // select date and photo

        binding.dob1.setOnClickListener {
            showDatePickerDialog()
        }

        binding.cv2.setOnClickListener {
            openGallery()
        }
        calendar = Calendar.getInstance()

        binding.updateBtn.setOnClickListener {
            saveDataToFirebase()
        }

    }

    private fun loadImage(imageUri: Uri?) {
        if (imageUri != null) {
            Glide.with(this)
                .load(imageUri)
                .circleCrop()
                .into(binding.cv2)
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            profileImageUri = data.data
            loadImage(profileImageUri)
        }
    }


    // date picker function
    private fun updateDOBTextView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.dob1.text = Editable.Factory.getInstance().newEditable(formattedDate)
    }

    private fun showDatePickerDialog() {
        val dateListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the DOB TextView with the selected date
                updateDOBTextView()
            }


        val datePickerDialog = DatePickerDialog(
            this,
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }

    // to save the data to firebase
    private fun saveDataToFirebase() {
        val user = FirebaseAuth.getInstance().currentUser

        val uid = user!!.uid


        val fullName = binding.fullname1.text.toString()
        val dob = binding.dob1.text.toString()
        val email = binding.email1.text.toString()
        val gender = binding.gender1.selectedItem.toString()


        if (fullName.isEmpty() || dob.isEmpty() || email.isEmpty() || gender.isEmpty() ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
        } else {
            // Check if a profile image is selected
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                if (profileImageUri != null) {
                    // Upload the image to Firebase Storage and get the download URL
                    val storageRef = FirebaseStorage.getInstance().getReference("Users").child("profileImages")
                    val imageRef = storageRef.child(uid)

                    imageRef.putFile(profileImageUri!!)
                        .addOnSuccessListener { taskSnapshot ->
                            // Get the download URL of the uploaded image
                            imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                                // Create a HashMap to store user data including the image URL
                                val userData = HashMap<String, Any>()
                                userData["uid"] = uid
                                userData["fullName"] = fullName
                                userData["dob"] = dob
                                userData["email"] = email
                                userData["gender"] = gender
                                userData["profileImageUrl"] = profileImageUri.toString() // Save the image URL

                                // Save the user data to Firebase Realtime Database
                                databaseReference.child(uid).setValue(userData)

                                // Display a Toast or perform any other actions upon successful data saving
                                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()

                                binding.fullname1.text?.clear()
                                binding.email1.text?.clear()
                                binding.dob1.text?.clear()

                                finish()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                this,
                                "Image upload failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(this, "Please select a profile image", Toast.LENGTH_SHORT).show()
                }
            } else {
                // User is not authenticated; handle accordingly
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }

        }
    }


    fun isEmailValid(email: String): Boolean {
        val regexPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        val pattern = Pattern.compile(regexPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}