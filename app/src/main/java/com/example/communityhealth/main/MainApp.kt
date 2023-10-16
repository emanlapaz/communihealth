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
    }
}