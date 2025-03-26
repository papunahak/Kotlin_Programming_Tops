package com.example.retrofitcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofitex1.databinding.ActivityLoginBinding
import com.example.retrofitex1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityLoginBinding
    lateinit var apiinterface: Apiinterface

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        apiinterface = ApiClient().getapiclient().create(Apiinterface::class.java)

        binding.btnlogin.setOnClickListener {

            var email = binding.edtemail.text.toString()
            var pass = binding.edtpass.text.toString()

            var call = apiinterface.signin(email,pass)

            call.enqueue(object: Callback<Model>
            {
                override fun onResponse(call: Call<Model>, response: Response<Model>) {

                    Toast.makeText(applicationContext, "Login Done", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }

                override fun onFailure(call: Call<Model>, t: Throwable)
                {
                    Toast.makeText(applicationContext, "Login Fail", Toast.LENGTH_SHORT).show()

                }
            })
        }
    }
}