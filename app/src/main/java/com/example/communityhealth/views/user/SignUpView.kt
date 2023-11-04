package com.example.communityhealth.views.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.communityhealth.R

class SignUpView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_view)

        setupCancelButon()
    }

    private fun setupCancelButon() {
        val cancelButton = findViewById<Button>(R.id.btn_cancel)

        cancelButton.setOnClickListener {
            goToLoginView()
        }
    }
    private fun goToLoginView() {
        val intent = Intent(this, LoginView::class.java)
        startActivity(intent)
        finish() // Close the SignUpActivity
    }
}
