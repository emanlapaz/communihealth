package com.example.communityhealth.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap

import com.example.communityhealth.databinding.ActivityPatientMapsBinding
import com.example.communityhealth.databinding.ContentPatientMapsBinding
import com.example.communityhealth.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class PatientMapsActivity :AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityPatientMapsBinding
    private lateinit var contentBinding: ContentPatientMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        contentBinding = ContentPatientMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

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
    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.patients.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.MRN).position(loc)
            map.setOnMarkerClickListener(this)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        contentBinding.currentMRN.text = marker.title
        return false
    }
}

