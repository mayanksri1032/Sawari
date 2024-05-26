package com.example.sawari.functions

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.sawari.R

class DropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drop)

        // Find the CardView with id foryou
        val forYouCardView: CardView = findViewById(R.id.foryou)

        // Set OnClickListener for the CardView
        forYouCardView.setOnClickListener {
            // Replace the fragment container with ForyouFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ForyouFragment.newInstance("param1", "param2"))
                .commit()
        }
    }
}
