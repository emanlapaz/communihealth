package com.example.communityhealth.views.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.R
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.views.patientlist.PatientListView

class LoginView: AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        setupLoginButton()
    }

    private fun setupLoginButton() {
        val loginButton = findViewById<Button>(R.id.btn_login_login)

        loginButton.setOnClickListener {
            navigateToPatientListView()
        }
    }

    private fun navigateToPatientListView() {
        val intent = Intent(this, PatientListView::class.java)
        startActivity(intent)
    }
}
