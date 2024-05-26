package com.example.sawari.Login

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sawari.ClaimActivity
import com.example.sawari.MainActivity
import com.example.sawari.NotificationActivity
import com.example.sawari.ProfileActivity
import com.example.sawari.R
import com.example.sawari.RAEActivity
import com.example.sawari.RewardActivity
import com.example.sawari.SafetyActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var firstNameTextView: TextView
    private lateinit var phoneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        firstNameTextView = findViewById(R.id.firstname)
        phoneTextView = findViewById(R.id.phone)

        loadUserDetails()

        val profileActivityImageView: ImageView = findViewById(R.id.profactivity)
        val backImageView: ImageView = findViewById(R.id.back)
        val helpLayout = findViewById<LinearLayout>(R.id.help)
        val paymentLayout = findViewById<LinearLayout>(R.id.payment)
        val ridesLayout = findViewById<LinearLayout>(R.id.rides)
        val safetyLayout = findViewById<LinearLayout>(R.id.safety)
        val referAndEarnLayout = findViewById<LinearLayout>(R.id.referandearn)
        val rewardsLayout = findViewById<LinearLayout>(R.id.rewards)
        val claimsLayout = findViewById<LinearLayout>(R.id.claims)
        val notificationsLayout = findViewById<LinearLayout>(R.id.notifications)

        profileActivityImageView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        backImageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        helpLayout.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        paymentLayout.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }

        ridesLayout.setOnClickListener {
            startActivity(Intent(this, RideActivity::class.java))
        }

        safetyLayout.setOnClickListener {
            startActivity(Intent(this, SafetyActivity::class.java))
        }

        referAndEarnLayout.setOnClickListener {
            startActivity(Intent(this, RAEActivity::class.java))
        }

        rewardsLayout.setOnClickListener {
            startActivity(Intent(this, RewardActivity::class.java))
        }

        claimsLayout.setOnClickListener {
            startActivity(Intent(this, ClaimActivity::class.java))
        }

        notificationsLayout.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
    }

    private fun loadUserDetails() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    firstNameTextView.text = document.getString("firstName")
                    phoneTextView.text = document.getString("phone")
                } else {
                    Toast.makeText(this, "No user details found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load user details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
