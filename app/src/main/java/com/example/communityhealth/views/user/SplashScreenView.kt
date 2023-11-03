package com.example.communityhealth.views.user

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.R

class SplashScreenView : AppCompatActivity() {

    private val SPLASH_DURATION = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        Handler(Looper.getMainLooper()).postDelayed({
            // The loading is complete, hide the progress bar
            progressBar.visibility = View.GONE

            val intent = Intent(this, LoginView::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DURATION)
    }
}
