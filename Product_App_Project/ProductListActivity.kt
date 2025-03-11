package com.example.productapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.product_app_task.R
import com.example.productapp.adapter.ProductAdapter
import com.example.productapp.api.ApiService
import com.example.productapp.model.Product
import com.example.productapp.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var addbtn: Button
    lateinit var recyclerView: RecyclerView
    lateinit var apiInterface: ApiService
    lateinit var itemlist: MutableList<Product>
    lateinit var adapter: ProductAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        searchView = findViewById(R.id.searchView)
        addbtn = findViewById(R.id.btnAddProduct)
        recyclerView = findViewById(R.id.recyclerView)
        itemlist = ArrayList<Product>()

        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        apiInterface = RetrofitClient().getapiclient().create(ApiService::class.java)// ✅ Use it directly, no `.create()`


        val call: Call<List<Product>> = apiInterface.getProducts()
        call.enqueue(object : Callback<List<Product>>
        {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>)
            {
                if (response.isSuccessful)
                {
                    itemlist = response.body() as MutableList<Product>
                    adapter = ProductAdapter(applicationContext, itemlist)
                    recyclerView.adapter = adapter
                }
                else
                {
                    Toast.makeText(applicationContext, "Failed to load products", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_SHORT).show()
            }
        })


        addbtn.setOnClickListener {
            startActivity(Intent(applicationContext, AddProductActivity::class.java))
            finish()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //  adapter.filter.f//i
                //Toast.makeText(applicationContext, "2", Toast.LENGTH_SHORT).show()
                filter1(newText)
                return false
            }
        })

    }

    private fun filter1(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Product> = ArrayList()

        // running a for loop to compare elements.
        for (item in itemlist) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.pname?.toLowerCase()?.contains(text.toLowerCase()) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }


    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}

