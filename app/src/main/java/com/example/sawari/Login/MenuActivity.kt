package com.example.sawari.Login

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sawari.menu.ClaimActivity
import com.example.sawari.menu.NotificationActivity
import com.example.sawari.menu.ProfileActivity
import com.example.sawari.R
import com.example.sawari.menu.RAEActivity
import com.example.sawari.menu.RewardActivity
import com.example.sawari.menu.SafetyActivity
import com.example.sawari.menu.HelpActivity
import com.example.sawari.menu.PaymentActivity
import com.example.sawari.menu.RideActivity
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

        val profileLayout = findViewById<LinearLayout>(R.id.profactivity)
        val helpLayout = findViewById<LinearLayout>(R.id.help)
        val paymentLayout = findViewById<LinearLayout>(R.id.payment)
        val ridesLayout = findViewById<LinearLayout>(R.id.rides)
        val safetyLayout = findViewById<LinearLayout>(R.id.safety)
        val referAndEarnLayout = findViewById<LinearLayout>(R.id.referandearn)
        val rewardsLayout = findViewById<LinearLayout>(R.id.rewards)
        val claimsLayout = findViewById<LinearLayout>(R.id.claims)
        val notificationsLayout = findViewById<LinearLayout>(R.id.notifications)

        profileLayout.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
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
