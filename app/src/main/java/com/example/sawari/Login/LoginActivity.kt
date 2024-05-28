package com.example.sawari.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sawari.MainActivity
import com.example.sawari.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Initially, disable the "Next" button
        binding.buttonNext.isEnabled = false

        binding.editTextPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Enable the "Next" button if the phone number contains exactly 10 digits
                val phoneNumber = s.toString()
                if (phoneNumber.length == 10) {
                    binding.buttonNext.isEnabled = true
                    binding.buttonNext.setBackgroundColor(Color.BLUE) // Change button color
                } else {
                    binding.buttonNext.isEnabled = false
                    binding.buttonNext.setBackgroundColor(Color.GRAY) // Reset button color
                }
            }
        })

        binding.buttonNext.setOnClickListener {
            val phoneNumber = binding.editTextPhoneNumber.text.toString()
            if (phoneNumber.length != 10) {
                Toast.makeText(this, "Please Enter a 10-digit Number", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("number", phoneNumber)
                startActivity(intent)
            }
        }
    }
}