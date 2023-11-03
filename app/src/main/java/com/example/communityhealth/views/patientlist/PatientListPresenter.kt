package com.example.communityhealth.views.patientlist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel
import com.example.communityhealth.views.map.PatientMapView
import com.example.communityhealth.views.patient.PatientView

class PatientListPresenter(val view: PatientListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getPatients() = app.patients.findAll()

    fun doAddPatient() {
        val launcherIntent = Intent(view, PatientView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditPatient(patient: PatientModel, pos: Int) {
        val launcherIntent = Intent(view, PatientView::class.java)
        launcherIntent.putExtra("patient_edit", patient)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowPatientsMap() {
        val launcherIntent = Intent(view, PatientMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun doLogOut() {
        view.navigateToLogin()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}