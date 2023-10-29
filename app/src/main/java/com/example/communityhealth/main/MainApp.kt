package com.example.communityhealth.main

import android.app.Application
import com.example.communityhealth.models.PatientJSONStore
import com.example.communityhealth.models.PatientMemStore
import com.example.communityhealth.models.PatientModel
import com.example.communityhealth.models.PatientStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    //val patients = ArrayList<PatientModel>()
    //val patients = PatientMemStore()

    lateinit var patients: PatientStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        patients = PatientJSONStore(applicationContext)
        i("CommunityHealth App started")
    }
}