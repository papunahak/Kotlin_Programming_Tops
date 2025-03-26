package com.example.sqlliteex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity()
{
    lateinit var edt1:EditText
    lateinit var edt2:EditText

    lateinit var btn1:Button
    lateinit var btn2:Button

    lateinit var dbHelper: DbHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt1 = findViewById(R.id.edtname)
        edt2 = findViewById(R.id.edtnum)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        dbHelper = DbHelper(applicationContext)

        btn1.setOnClickListener {

            var name  = edt1.text.toString()
            var num = edt2.text.toString()

            var m = Model()
            m.name=name
            m.num=num

            var id = dbHelper.insertdata(m)
            Toast.makeText(applicationContext, "RECORD INSERTED: "+id, Toast.LENGTH_SHORT).show()
        }

        btn2.setOnClickListener {

            startActivity(Intent(applicationContext,ViewActivity::class.java))

        }

    }
}