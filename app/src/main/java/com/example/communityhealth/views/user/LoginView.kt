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
        setupSignUpButton()
    }

    private fun setupLoginButton() {
        val loginButton = findViewById<Button>(R.id.btn_login_login)

        loginButton.setOnClickListener {
            goToPatientListView()
        }
    }
    private fun setupSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.btn_signup_signup)

        signUpButton.setOnClickListener {
            goToSignUpView()
        }
    }

    private fun goToPatientListView() {
        val intent = Intent(this, PatientListView::class.java)
        startActivity(intent)
    }

    private fun goToSignUpView() {
        val intent = Intent(this, SignUpView::class.java) // Replace SignUpActivity with the actual name of your sign-up activity
        startActivity(intent)
    }
}
