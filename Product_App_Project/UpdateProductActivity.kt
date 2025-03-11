package com.example.productapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.product_app_task.R
import com.example.productapp.api.ApiService
import com.example.productapp.model.Product
import com.example.productapp.utils.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var etPname: EditText
    private lateinit var etPprice: EditText
    private lateinit var etPdesc: EditText
    private lateinit var etPstatus: EditText
    private lateinit var btnUpdateProduct: Button
    private lateinit var imgProduct: ImageView
    private var imageUri: Uri? = null
    private var productId: Int = -1
    lateinit var apiInterface: ApiService

    companion object {
        private const val RESULT_SELECT_IMAGE = 100 // Request code for image selection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        // Initialize Views
        etPname = findViewById(R.id.etPname)
        etPprice = findViewById(R.id.etPprice)
        etPdesc = findViewById(R.id.etPdesc)
        etPstatus = findViewById(R.id.etPstatus)
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct)
        imgProduct = findViewById(R.id.imgProduct)
        apiInterface = RetrofitClient().getapiclient().create(ApiService::class.java)

        // Receive Product Object from Intent
        val product: Product? = intent.getParcelableExtra("PRODUCT")

        if (product == null) {
            Toast.makeText(this, "Error loading product details", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Store product ID for API update
        productId = product.pid

        // Populate fields with existing product details
        etPname.setText(product.pname)
        etPprice.setText(product.pprice)
        etPdesc.setText(product.pdesc)
        etPstatus.setText(product.pstatus)

        // Load existing product image
        if (!product.pimage.isNullOrEmpty()) {
            Glide.with(this).load(product.pimage).into(imgProduct)
        } else {
            imgProduct.setImageResource(R.drawable.ic_launcher_background) // Placeholder image
        }

        // Select a new image from the gallery
        imgProduct.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, RESULT_SELECT_IMAGE)
        }

        // Update Product Button Click
        btnUpdateProduct.setOnClickListener {
            if (etPname.text.isEmpty() || etPprice.text.isEmpty() || etPdesc.text.isEmpty() || etPstatus.text.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                updateProductWithImage()
            }
        }
    }

    // Handle Image Selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data?.data != null) {
            imageUri = data.data
            Glide.with(this).load(imageUri).into(imgProduct) // Show selected image preview
        }
    }

    // Update Product with Image

    private fun updateProductWithImage() {
        var pname = etPname.text.toString()
        var pprice = etPprice.text.toString()
        var pdes = etPdesc.text.toString()
        var pstatus = etPstatus.text.toString()

        val name: RequestBody = RequestBody.create(MultipartBody.FORM, pname)
        val price: RequestBody = RequestBody.create(MultipartBody.FORM, pprice)
        val desc: RequestBody = RequestBody.create(MultipartBody.FORM, pdes)
        val status: RequestBody = RequestBody.create(MultipartBody.FORM, pstatus)
        var imagePart: MultipartBody.Part? = null

        // ✅ Convert URI to File if a new image is selected
        if (imageUri != null) {
            val filePath = getRealPathFromURI(imageUri!!)
            if (filePath != null) {
                val file = File(filePath)
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                imagePart = MultipartBody.Part.createFormData("pimage", file.name, requestFile)
            }
        }

        // ✅ Send API request
        apiInterface.updateProduct(
            RequestBody.create("text/plain".toMediaTypeOrNull(), productId.toString()),
            pname,
            pprice,
            pdes,
            imagePart,  // ✅ This will send an image if selected
            pstatus
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateProductActivity, "Product Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateProductActivity, "Failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@UpdateProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // ✅ Helper Function to Get Real File Path from URI
    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)) else null
        }
    }


}
