package com.example.communityhealth.models

data class PatientModel(var MRN:String = "",
                        var lastName: String = "",
                        var firstName: String = "")

interface PatientStore {
    fun findAll(): List<PatientModel>
    fun create(patient: PatientModel)
}