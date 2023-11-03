package com.example.communityhealth.views.patient

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.databinding.ActivityPatientBinding
import com.example.communityhealth.helpers.showImagePicker
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.Location
import com.example.communityhealth.models.PatientModel
import com.example.communityhealth.views.editlocation.EditLocationView
import timber.log.Timber

class PatientPresenter(private val view: PatientView) {

    var patient = PatientModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityPatientBinding = ActivityPatientBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("patient_edit")) {
            edit = true
            patient = view.intent.extras?.getParcelable("patient_edit")!!
            view.showPatient(patient)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(MRN: String, firstName: String, lastName: String) {
        patient.MRN = MRN
        patient.firstName = firstName
        patient.lastName = lastName
        if (edit) {
            app.patients.update(patient)
        } else {
            app.patients.create(patient)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.patients.delete(patient)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }

    fun doSetLocation() {
        val location = Location(53.2158300, -6.6669400, 15f)
        if (patient.zoom != 0f) {
            location.lat =  patient.lat
            location.lng = patient.lng
            location.zoom = patient.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cachePatient (MRN: String, firstName: String, lastName: String) {
        patient.MRN = MRN;
        patient.firstName = firstName;
        patient.lastName = lastName;
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            patient.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(patient.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(patient.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            patient.lat = location.lat
                            patient.lng = location.lng
                            patient.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}