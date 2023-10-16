package com.example.communityhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.communityhealth.databinding.ActivityPatientBinding
import com.example.communityhealth.models.PatientModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.Forest.i
import java.util.Locale

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    var patient = PatientModel()
    val patients = ArrayList<PatientModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("CommunityHealth Activity started...")

        binding.btnAdd.setOnClickListener {
            val patientMRN = binding.patientMRN.text.toString().trim()
            val lastName = binding.patientLastName.text.toString().trim()
            val firstName = binding.patientFirstName.text.toString().trim()

            val regexPattern = Regex("^[0-9]{6}$") // Accept only 6-digit integers
            val nameRegexPattern = Regex("^[a-zA-Z]+\$") // Accept only letters in the name fields

            if (regexPattern.matches(patientMRN) && lastName.isNotEmpty() && firstName.isNotEmpty() && nameRegexPattern.matches(lastName) && nameRegexPattern.matches(firstName)) {
                i("add Button Pressed: $patientMRN")
                patient.MRN = patientMRN
                patient.lastName = lastName
                patient.firstName = firstName
                patients.add(patient.copy())

                Snackbar
                    .make(it, "Patient added", Snackbar.LENGTH_SHORT)
                    .show()

                for (i in patients.indices) {
                    i("PatientMRN[$i]: ${patients[i]}")
                }
            } else {
                var errorPrompt = "Please correct the following issues:\n"

                if (!regexPattern.matches(patientMRN)) {
                    errorPrompt += " - MRN must be a 6-digit number\n"
                }
                if (lastName.isEmpty()) {
                    errorPrompt += " - Last name can't be empty\n"
                }
                if (firstName.isEmpty()) {
                    errorPrompt += " - First name can't be empty\n"
                }
                if (!nameRegexPattern.matches(lastName)) {
                    errorPrompt += " - Last name can only contain letters\n"
                }
                if (!nameRegexPattern.matches(firstName)) {
                    errorPrompt += " - First name can only contain letters\n"
                }

                Snackbar
                    .make(it, errorPrompt, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}

// work on the adapters