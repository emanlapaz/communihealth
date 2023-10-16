package com.example.communityhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.communityhealth.databinding.ActivityPatientBinding
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.Forest.i
import java.util.Locale

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    var patient = PatientModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("CommunityHealth Activity started...")

        binding.btnAdd.setOnClickListener {
            patient.MRN = binding.patientMRN.text.toString().trim()
            patient.lastName = binding.patientLastName.text.toString().trim()
            patient.firstName = binding.patientFirstName.text.toString().trim()

            patient.lastName = patient.lastName.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } //Ensures First Letter is Capitalized

            patient.firstName = patient.firstName.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } //Ensures First Letter is Capitalized

            val regexPattern = Regex("^[0-9]{6}$") // Accept only 6-digit integers
            val nameRegexPattern = Regex("^[a-zA-Z]+\$") // Accept only letters in the name fields

            if (regexPattern.matches(patient.MRN) && patient.lastName.isNotEmpty() && patient.firstName.isNotEmpty() && nameRegexPattern.matches(patient.lastName) && nameRegexPattern.matches(patient.firstName)) {
                app.patients.add(patient.copy())
                i("add Button Pressed: ${patient.MRN}")

                Snackbar
                    .make(it, "Patient added", Snackbar.LENGTH_SHORT)
                    .show()

                for (i in app!!.patients.indices) {
                    i("PatientMRN[$i]: ${this.app.patients[i]}")
                }
            } else {
                var errorPrompt = "Please correct the following issues:\n"

                if (!regexPattern.matches(patient.MRN)) {
                    errorPrompt += " - MRN must be a 6-digit number\n"
                }
                if (patient.lastName.isEmpty()) {
                    errorPrompt += " - Last name can't be empty\n"
                }
                if (patient.firstName.isEmpty()) {
                    errorPrompt += " - First name can't be empty\n"
                }
                if (!nameRegexPattern.matches(patient.lastName)) {
                    errorPrompt += " - Last name can only contain letters\n"
                }
                if (!nameRegexPattern.matches(patient.firstName)) {
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