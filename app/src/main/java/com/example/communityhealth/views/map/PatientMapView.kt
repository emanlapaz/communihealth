package com.example.communityhealth.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.databinding.ActivityPatientMapsBinding
import com.example.communityhealth.databinding.ContentPatientMapsBinding
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso

class PatientMapView : AppCompatActivity() , GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityPatientMapsBinding
    private lateinit var contentBinding: ContentPatientMapsBinding
    lateinit var app: MainApp
    lateinit var presenter: PatientMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityPatientMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = PatientMapPresenter(this)

        contentBinding = ContentPatientMapsBinding.bind(binding.root)

        //Initialize map and populate with markers
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            presenter.doPopulateMap(it)
        }
    }

    //Displays patient data when the map is clicked
    fun showPatient(patient: PatientModel) {
        contentBinding.currentMRN.text = patient.MRN
        contentBinding.currentFirstName.text = patient.firstName
        contentBinding.currentLastName.text = patient.lastName
        contentBinding.currentEircode.text = patient.eircode
        contentBinding.currentRoad.text = patient.road
        contentBinding.currentTown.text = patient.town

        Picasso.get()
            .load(patient.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
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