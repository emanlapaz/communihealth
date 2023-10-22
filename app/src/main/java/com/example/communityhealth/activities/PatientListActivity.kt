package com.example.communityhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.communityhealth.R
import com.example.communityhealth.main.MainApp

class PatientListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)
        app = application as MainApp
    }
}