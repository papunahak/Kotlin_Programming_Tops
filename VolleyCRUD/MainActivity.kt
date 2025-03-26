package com.example.onlinedatajson

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var list: MutableList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list1)
        list = ArrayList()

        val url = "https://simplifiedcoding.net/demos/view-flipper/heroes.php" // Use your new JSON URL

        val stringRequest = StringRequest(Request.Method.GET, url, object : Response.Listener<String> {
            override fun onResponse(response: String?) {
                response?.let {
                    val jsonObject = JSONObject(it)
                    val jsonArray = jsonObject.getJSONArray("heroes") // Access "heroes" array

                    for (i in 0 until jsonArray.length()) {
                        val jsonobject = jsonArray.getJSONObject(i)

                        val name = jsonobject.getString("name")
                        val imageurl = jsonobject.getString("imageurl")

                        val m = Model()
                        m.name=name
                        m.imageurl=imageurl
                        list.add(m)
                    }

                    val adapter = MyAdapter(applicationContext, list)
                    listView.adapter = adapter
                }
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_SHORT).show()
            }
        })

        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }
}
