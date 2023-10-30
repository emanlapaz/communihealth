package com.example.communityhealth.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap

import com.example.communityhealth.databinding.ActivityPatientMapsBinding
import com.example.communityhealth.databinding.ContentPatientMapsBinding


class PatientMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientMapsBinding
    private lateinit var contentBinding: ContentPatientMapsBinding
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentPatientMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}

