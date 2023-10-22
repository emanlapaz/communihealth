package com.example.communityhealth.main

import android.app.Application
import com.example.communityhealth.models.PatientModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val patients = ArrayList<PatientModel>()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("CommuniHealth started")
        patients.add(PatientModel(MRN = "123123", lastName = "DOe", firstName = "John"))
        patients.add(PatientModel(MRN = "321321", lastName = "Didi", firstName = "Ed"))
        patients.add(PatientModel(MRN = "121212", lastName = "DitOe", firstName = "Bill"))

    }
}