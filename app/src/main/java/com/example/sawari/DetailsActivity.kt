package com.example.sawari

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var city: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        firstName = findViewById(R.id.firstname)
        lastName = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        city = findViewById(R.id.city)
        submitButton = findViewById(R.id.submit)

        val cityName = intent.getStringExtra("city")
        city.setText(cityName)

        submitButton.setOnClickListener {
            val firstNameText = firstName.text.toString().trim()
            val lastNameText = lastName.text.toString().trim()
            val emailText = email.text.toString().trim()
            val phoneText = phone.text.toString().trim()
            val cityText = city.text.toString().trim()

            if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() || cityText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            } else {
                saveUserDetails(firstNameText, lastNameText, emailText, phoneText, cityText)
            }
        }
    }

    private fun saveUserDetails(firstName: String, lastName: String, email: String, phone: String, city: String) {
        val uid = auth.currentUser?.uid ?: return
        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phone" to phone,
            "city" to city
        )

        db.collection("users").document(uid).set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
