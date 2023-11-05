package com.example.communityhealth.views.map

import com.example.communityhealth.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
class PatientMapPresenter(val view: PatientMapView) {
    var app: MainApp

    init {
        app = view.application as MainApp
    }

    // Populates maps wiht Patient Markers
    fun doPopulateMap(map: GoogleMap) {

        map.uiSettings.setZoomControlsEnabled(true)

        map.setOnMarkerClickListener(view)

        app.patients.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.MRN).position(loc)

            //Adds a marker in the map with the Patient ID
            map.addMarker(options)?.tag = it.id

            //Move Camera to patients location
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }
    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val patient = app.patients.findById(tag)
        if (patient != null) view.showPatient(patient)
    }
}