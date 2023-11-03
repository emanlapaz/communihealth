package com.example.communityhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import com.example.communityhealth.R
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.views.patientlist.PatientListView

class LoginActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        val loginButton = findViewById<Button>(R.id.btn_login_login)

        loginButton.setOnClickListener {

            val intent = Intent(this, PatientListView::class.java)

            startActivity(intent)
        }
    }
}
