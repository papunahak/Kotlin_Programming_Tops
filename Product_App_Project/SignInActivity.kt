package com.example.productapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.product_app_task.R
import com.example.productapp.api.ApiService
import com.example.productapp.model.User
import com.example.productapp.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button
    lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignIn.setOnClickListener { loginUser() }
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, Signupactivities::class.java))
        }
    }

    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }
        apiService = RetrofitClient().getapiclient().create(ApiService::class.java)
        apiService.signIn(email, password)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(this@SignInActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignInActivity, ProductListActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@SignInActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@SignInActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
