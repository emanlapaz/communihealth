package com.example.communityhealth.models

import timber.log.Timber.Forest.i

class PatientMemStore : PatientStore {

    val patients = ArrayList<PatientModel>()

    override fun findAll(): List<PatientModel> {
        return patients
    }
    override fun create(patient: PatientModel) {
        patients.add(patient)
        logAll()
    }
    fun logAll() {
        patients.forEach{ i("$it") }
    }
}