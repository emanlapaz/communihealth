package com.example.communityhealth.views.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.communityhealth.R
import com.example.communityhealth.views.patientlist.PatientListView

class welcomeSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_splash)
        val userName = intent.getStringExtra("userName")

        val welcomeTextView = findViewById<TextView>(R.id.welcomeSplash)
        welcomeTextView.text = "Welcome $userName"

        // Use a Handler to delay the transition to the main screen
        val handler = Handler()
        handler.postDelayed({
            // Start the main screen of your application
            val intent = Intent(this, PatientListView::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}