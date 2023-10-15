package com.example.communityhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.communityhealth.databinding.ActivityPatientBinding
import com.example.communityhealth.models.PatientModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.Forest.i

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    var patient = PatientModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("CommunityHealth Activity started...")

        binding.btnAdd.setOnClickListener() {
            val patientName = binding.patientName.text.toString()
            if (patientName.isNotEmpty()) {
                i("add Button Pressed: $patientName")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
