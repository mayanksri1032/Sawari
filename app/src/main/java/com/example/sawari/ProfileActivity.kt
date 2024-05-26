package com.example.sawari

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sawari.Login.LoginActivity
import com.example.sawari.Login.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var city: TextView
    private lateinit var deleteAccountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val backImageView: ImageView = findViewById(R.id.menuback)

        backImageView.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        firstName = findViewById(R.id.firstname)
        lastName = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        city = findViewById(R.id.city)
        deleteAccountTextView = findViewById(R.id.DeleteAccount)

        loadUserDetails()

        deleteAccountTextView.setOnClickListener {
            deleteAccount()
        }
    }

    private fun loadUserDetails() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    firstName.text = document.getString("firstName")
                    lastName.text = document.getString("lastName")
                    email.text = document.getString("email")
                    phone.text = document.getString("phone")
                    city.text = document.getString("city")
                } else {
                    Toast.makeText(this, "No user details found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load user details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteAccount() {
        val user = auth.currentUser
        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    Toast.makeText(this, "Your account has been deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete account: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
