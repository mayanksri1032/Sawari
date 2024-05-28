package com.example.sawari.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.sawari.R
import com.example.sawari.help.TicketActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_help)
        val forYouCardView: CardView = findViewById(R.id.ticket)

// Set OnClickListener for the CardView
        forYouCardView.setOnClickListener {
            // Create an Intent to start TicketActivity
            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)
        }

    }
}