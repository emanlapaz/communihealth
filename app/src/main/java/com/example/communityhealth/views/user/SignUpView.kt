package com.example.communityhealth.views.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.communityhealth.R
import com.example.communityhealth.models.UserJSONStore
import com.example.communityhealth.models.UserModel
import com.google.android.material.snackbar.Snackbar

class SignUpView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_view)

        setupSignUpButton()
        setupCancelButon()
    }

    private fun setupCancelButon() {
        val cancelButton = findViewById<Button>(R.id.btn_cancel)

        cancelButton.setOnClickListener {
            goToLoginView()
        }
    }
    private fun setupSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.btn_signup)
        val usernameEditText = findViewById<EditText>(R.id.et_signup_username)
        val passwordEditText = findViewById<EditText>(R.id.et_signup_password)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Create a UserModel object
                val user = UserModel(0, username, password)

                // Initialize UserJSONStore
                val userStore = UserJSONStore(this)

                userStore.create(user)
                // Redirect to the login view
                goToLoginView()
            } else {
                // Show if username or password is empty
                showEmptyFieldSnackbar()
            }
        }
    }

    private fun showEmptyFieldSnackbar() {
        val rootView = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(
            rootView,
            "Username and password cannot be empty",
            Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }

    private fun goToLoginView() {
        val intent = Intent(this, LoginView::class.java)
        startActivity(intent)
        finish()
    }
}
