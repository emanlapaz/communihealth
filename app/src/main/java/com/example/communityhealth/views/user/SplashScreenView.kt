package com.example.communityhealth.views.user

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.R

class SplashScreenView : AppCompatActivity() {

    private val SPLASH_DURATION = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginView::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DURATION)
    }
}
