package com.example.productapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product_app_task.R
import com.example.productapp.ProductListActivity
import com.example.productapp.UpdateProductActivity
//import com.example.productapp.ViewProduct
import com.example.productapp.api.ApiService
import com.example.productapp.model.Product
import com.example.productapp.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class ProductAdapter(var context: Context, var productList: MutableList<Product>) : RecyclerView.Adapter<ProductViewHolder>()
{
    var id = 0
    var apiService: ApiService=RetrofitClient().getapiclient().create(ApiService::class.java)// ✅ Use it directly, no `.create()`

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        var layout = LayoutInflater.from(parent.context)
        var layoutview = layout.inflate(R.layout.item_product,parent,false)
        return ProductViewHolder(layoutview)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, @SuppressLint("RecyclerView") position: Int)
    {
        id = productList.get(position).pid
        holder.productname.setText(productList.get(position).pname)
        holder.productdesc.setText(productList.get(position).pdesc)
        holder.productprice.setText(productList.get(position).pprice)
        Glide
            .with(holder.itemView.context)
            .load(productList.get(position).pimage)
            .centerCrop()
            .into(holder.productimg)

        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("PRODUCT", productList[position]) // Send entire product object
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.delete.setOnClickListener {
            val alertDialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Product?")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes") { _, _ ->
                    val call: Call<Void> = apiService.DeleteProduct(productList[position].pid)
                    call.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                productList.removeAt(position)
                                notifyDataSetChanged()
                                Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                .setNegativeButton("No", null)
                .create()
            alertDialog.show()
        }

        holder.activebtn.setOnClickListener {

            var alertDialog = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle("Which Operation Do You Perform ?? ")
            alertDialog.setPositiveButton("UPDATE", { dialogInterface: DialogInterface, i: Int ->

                var i = Intent(context,UpdateProductActivity::class.java)
                i.putExtra("pname",productList.get(position).pname)
                i.putExtra("pprice",productList.get(position).pprice)
                i.putExtra("pdes",productList.get(position).pdesc)
                i.putExtra("pstatus",productList.get(position).pstatus)
                i.putExtra("id",productList.get(position).pid)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)

            })
            alertDialog.setNegativeButton("DELETE", { dialogInterface: DialogInterface, i: Int ->

                var call: Call<Void> = apiService.DeleteProduct(productList.get(position).pid)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>)
                    {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()

                        var i = Intent(context, ProductListActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(i)
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable)
                    {
                        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                    }

                })

            })

            alertDialog.show()
        }
    }

    override fun getItemCount(): Int
    {
        return productList.size
    }

    fun filterList(filterlist: ArrayList<Product>) {
        // below line is to add our filtered
        // list in our course array list.
        productList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}

class ProductViewHolder(layoutview: View) : RecyclerView.ViewHolder(layoutview) {
    var productimg : ImageView = layoutview.findViewById(R.id.etPimage)
    var productname : TextView = layoutview.findViewById(R.id.etPname)
    var productdesc : TextView = layoutview.findViewById(R.id.etPdesc)
    var productprice : TextView = layoutview.findViewById(R.id.tvPprice)
    var activebtn : Button = layoutview.findViewById(R.id.activebtn)
    var update : ImageView = layoutview.findViewById(R.id.btnUpdate)
    var delete : ImageView = layoutview.findViewById(R.id.btnDelete)
}

