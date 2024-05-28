package com.example.sawari.functions

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.sawari.Login.MenuActivity
import com.example.sawari.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profActivityImageView: ImageView = findViewById(R.id.menuactivity)

        profActivityImageView.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // Find the CardViews for autocard, bikecard, and carcard
        val autocard: CardView = findViewById(R.id.autocard)
        val bikecard: CardView = findViewById(R.id.bikecard)
        val carcard: CardView = findViewById(R.id.carcard)

        // Set OnClickListener for autocard
        autocard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener for bikecard
        bikecard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener for carcard
        carcard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }
    }
}
