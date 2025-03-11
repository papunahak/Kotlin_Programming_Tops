package com.example.productapp.api


import com.example.productapp.model.Product  // ✅ CORRECT IMPORT
import com.example.productapp.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("signup.php")
    @FormUrlEncoded
    fun signUp(
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("mobile") mobile: String
    ): Call<Void>

    @POST("signin.php")
    @FormUrlEncoded
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>

    @Multipart
    @POST("productinsert.php")
    fun InsertProduct (
        @Part("pname") pname:RequestBody,
        @Part("pprice") pprice:RequestBody,
        @Part("pdesc") pdesc:RequestBody,
        @Part("pstatus") pstatus: RequestBody,
        @Part pimage: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("productview.php")  // ✅ Ensure correct API endpoint
    fun getProducts(): Call<List<Product>>

    @Multipart
    @POST("productupdate.php")
    fun updateProduct(
        @Part("pid") pid: RequestBody,
        @Part("pname") pname: String,
        @Part("pprice") pprice: String,
        @Part("pdesc") pdesc: String,
        @Part pimage: MultipartBody.Part?,  // ✅ Nullable to allow updates without an image
        @Part("pstatus") pstatus: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("productdelete.php")
    fun DeleteProduct(
        @Field("pid") pid:Int,
    ): Call<Void>

}
