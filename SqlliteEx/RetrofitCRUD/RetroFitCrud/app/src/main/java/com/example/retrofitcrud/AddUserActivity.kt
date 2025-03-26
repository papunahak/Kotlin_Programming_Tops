package com.example.retrofitcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofitex1.databinding.ActivityAdduserBinding
import com.example.retrofitex1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdduserActivity : AppCompatActivity()
{
    lateinit var binding:ActivityAdduserBinding
    lateinit var apiinterface: Apiinterface

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityAdduserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        apiinterface = ApiClient().getapiclient().create(Apiinterface::class.java)

        binding.btn1.setOnClickListener {

            var pname = binding.edt1.text.toString()
            var pprice = binding.edt2.text.toString()
            var pdes = binding.edt3.text.toString()

            var call: Call<Void> = apiinterface.insertdata(pname,pprice,pdes)
            call.enqueue(object:Callback<Void>
            {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    Toast.makeText(applicationContext, "INSERTED", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                    Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_SHORT).show()

                }
            })
        }
    }
}