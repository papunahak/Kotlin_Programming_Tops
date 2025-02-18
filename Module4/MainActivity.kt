package com.example.module_4praticle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Finding UI Elements
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()


            if (name.isEmpty()) {
                etName.error = "Name is required"
                return@setOnClickListener
            }


            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Invalid email format"
                return@setOnClickListener
            }


            if (!phone.matches(Regex("^[0-9]{10}$"))) {
                etPhone.error = "Enter a valid 10-digit phone number"
                return@setOnClickListener
            }

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
        }
    }
}
