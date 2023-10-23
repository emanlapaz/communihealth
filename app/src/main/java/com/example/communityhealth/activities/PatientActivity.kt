package com.example.communityhealth.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.communityhealth.R
import com.example.communityhealth.databinding.ActivityPatientBinding
import com.example.communityhealth.helpers.showImagePicker
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.Forest.i
import java.util.Locale

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    var patient = PatientModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false

        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("CommunityHealth Activity started...")

        if (intent.hasExtra("patient_edit")) {
            edit = true
            patient = intent.extras?.getParcelable("patient_edit")!!
            binding.patientMRN.setText(patient.MRN)
            binding.patientLastName.setText(patient.lastName)
            binding.patientFirstName.setText(patient.firstName)
            binding.btnAdd.setText(R.string.save_patient)
            Picasso.get()
                .load(patient.image)
                .into(binding.patientImage)
            if (patient.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_patient_image)
            }
        }

        binding.btnAdd.setOnClickListener {
            patient.MRN = binding.patientMRN.text.toString().trim()
            patient.lastName = binding.patientLastName.text.toString().trim()
            patient.firstName = binding.patientFirstName.text.toString().trim()

            patient.lastName = patient.lastName.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } // Ensures First Letter is Capitalized

            patient.firstName = patient.firstName.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } // Ensures First Letter is Capitalized

            val regexPattern = Regex("^[0-9]{6}$") // Accept only 6-digit integers
            val nameRegexPattern = Regex("^[a-zA-Z]+\$") // Accept only letters in the name fields

            if (regexPattern.matches(patient.MRN) && patient.lastName.isNotEmpty() && patient.firstName.isNotEmpty() && nameRegexPattern.matches(
                    patient.lastName
                ) && nameRegexPattern.matches(patient.firstName)
            ) {

                if (intent.hasExtra("patient_edit")) {
                    // If the "patient_edit" extra is present, update the existing patient
                    app.patients.update(patient)
                    i("Button Pressed: Edit - MRN: ${patient.MRN}")
                } else {
                    // If the extra is not present, create a new patient
                    app.patients.create(patient.copy())
                    i("Button Pressed: Add - MRN: ${patient.MRN}")
                }

                setResult(RESULT_OK)
                finish()

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
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.patient_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            patient.image = result.data!!.data!!
                            Picasso.get()
                                .load(patient.image)
                                .into(binding.patientImage)
                            binding.chooseImage.setText(R.string.change_patient_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}
