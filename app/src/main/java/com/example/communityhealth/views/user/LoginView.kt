package com.example.communityhealth.views.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.R
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.UserJSONStore
import com.example.communityhealth.views.patientlist.PatientListView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            val usernameEditText = findViewById<EditText>(R.id.et_login_username)
            val passwordEditText = findViewById<EditText>(R.id.et_login_password)

            val enteredUsername = usernameEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            // Check if the entered username and password match any stored user
            checkUserCredentials(enteredUsername, enteredPassword)
        }
    }
    private fun setupSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.btn_signup_signup)

        signUpButton.setOnClickListener {
            goToSignUpView()
        }
    }
    private fun checkUserCredentials(enteredUsername: String, enteredPassword: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val userStore = UserJSONStore(this@LoginView)

            val users = userStore.findAll()

            val matchingUser = users.find { user ->
                user.username == enteredUsername && user.password == enteredPassword
            }

            if (matchingUser != null) {
                // Successful login
                // Save the logged-in username in shared preferences
                val sharedPreferences = getSharedPreferences("YourPrefName", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("LoggedInUserNameKey", matchingUser.username).apply()

                // Start the SplashScreenActivity and pass the username
                val intent = Intent(this@LoginView, welcomeSplash::class.java).apply {
                    putExtra("userName", matchingUser.username)
                }
                startActivity(intent)
                finish()
            } else {
                // Invalid login
                showLoginErrorSnackbar()
            }
        }
    }


    private fun showLoginErrorSnackbar() {
        val rootView = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(
            rootView,
            "Invalid username or password",
            Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }

    private fun goToSignUpView() {
        val intent = Intent(this, SignUpView::class.java) // Replace SignUpActivity with the actual name of your sign-up activity
        startActivity(intent)
    }
}
