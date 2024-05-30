package com.example.sawari.functions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.sawari.Login.MenuActivity
import com.example.sawari.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.card.MaterialCardView
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var gmap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocationText: EditText
    private lateinit var dropLocationText: EditText
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val profActivityImageView: ImageView = findViewById(R.id.menuactivity)
        currentLocationText = findViewById(R.id.currentlocationtext)
        dropLocationText = findViewById(R.id.droplocation)

        profActivityImageView.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val autocard: CardView = findViewById(R.id.autocard)
        val bikecard: CardView = findViewById(R.id.bikecard)
        val carcard: CardView = findViewById(R.id.carcard)
        val materialCardView: MaterialCardView = findViewById(R.id.materialCardView)
        val materialCard: MaterialCardView = findViewById(R.id.materialCard)

        autocard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        bikecard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        carcard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        materialCardView.setOnClickListener {
            val intent = Intent(this, PickupActivity::class.java)
            startActivity(intent)
        }

        materialCard.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        // Add the listener for the dropLocationText
        dropLocationText.setOnClickListener {
            val intent = Intent(this, DropActivity::class.java)
            startActivity(intent)
        }

        // Add the listener for the currentLocationText
        currentLocationText.setOnClickListener {
            val intent = Intent(this, PickupActivity::class.java)
            startActivity(intent)
        }

        requestLocationPermission()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap?.setOnMapClickListener { latLng ->
            updateLocation(latLng)
        }
        getCurrentLocation()
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getCurrentLocation() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null && gmap != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        updateLocation(currentLatLng)
                    } else {
                        Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permission error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLocation(latLng: LatLng) {
        gmap?.let { map ->
            currentMarker?.remove()
            currentMarker = map.addMarker(MarkerOptions().position(latLng).title("You are here"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            val address = getAddressFromLatLng(latLng.latitude, latLng.longitude)
            currentLocationText.setText(address)
        }
    }



    private fun getAddressFromLatLng(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            address.getAddressLine(0) ?: "Address not found"
        } else {
            "Address not found"
        }
    }

}