package com.example.communityhealth.models
interface PatientStore {
    fun findAll(): List<PatientModel>
    fun create(patient: PatientModel)
    fun update(patient: PatientModel)
    fun delete(patient: PatientModel)
}