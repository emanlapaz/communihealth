package com.example.communityhealth.models

import timber.log.Timber.Forest.i

var lastId = 0L
internal fun getId(): Long {
    return lastId++
}
class PatientMemStore : PatientStore {

    val patients = ArrayList<PatientModel>()

    override fun findAll(): List<PatientModel> {
        return patients
    }

    override fun create(patient: PatientModel) {
        patient.id = getId()
        patients.add(patient)
        logAll()
    }

    override fun update(patient: PatientModel) {
        var foundPatient: PatientModel? = patients.find { p -> p.id == patient.id }
        if (foundPatient != null) {
            foundPatient.MRN = patient.MRN
            foundPatient.firstName = patient.firstName
            foundPatient.lastName = patient.lastName
            foundPatient.image = patient.image
            foundPatient.lat = patient.lat
            foundPatient.lng = patient.lng
            foundPatient.zoom = patient.zoom
            logAll()
        }
    }

    override fun delete(patient: PatientModel) {
        patients.remove(patient)
    }
        private fun logAll() {
            patients.forEach { i("${it}") }
        }

    override fun findById(id:Long) : PatientModel? {
        val foundPatient: PatientModel? = patients.find { it.id == id }
        return foundPatient
    }
}
