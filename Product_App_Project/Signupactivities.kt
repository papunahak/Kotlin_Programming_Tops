package com.example.productapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.product_app_task.R
import com.example.productapp.api.ApiService
import com.example.productapp.model.User
import com.example.productapp.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signupactivities : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etSurname: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etMobile: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var btnSignUp: Button
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etName = findViewById(R.id.etName)
        etSurname = findViewById(R.id.etSurname)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etMobile = findViewById(R.id.etMobile)
        radioGroupGender = findViewById(R.id.rgGender)
        btnSignUp = findViewById(R.id.btnSignUp)
        apiService = RetrofitClient().getapiclient().create(ApiService::class.java)

        btnSignUp.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = etName.text.toString().trim()
        val surname = etSurname.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val mobile = etMobile.text.toString().trim()
        val selectedGenderId = radioGroupGender.checkedRadioButtonId

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || mobile.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedGender = findViewById<RadioButton>(selectedGenderId).text.toString()
        apiService.signUp(name, surname, email, password, selectedGender, mobile)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Signupactivities, "User Registered", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Signupactivities, SignInActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@Signupactivities, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@Signupactivities, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
