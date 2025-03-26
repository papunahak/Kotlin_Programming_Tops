package com.example.retrofitcrud

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Apiinterface
{
    @FormUrlEncoded
    @POST("productinsert.php")
    fun insertdata
                (
        @Field("product_name") product_name:String,
        @Field("product_price") product_price:String,
        @Field("product_description") product_description:String
    ): Call<Void>

    @FormUrlEncoded
    @POST("signup.php")
    fun signup
                (
        @Field("firstname") firstname:String,
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<Void>

    @FormUrlEncoded
    @POST("signin.php")
    fun signin
                (

        @Field("email") email:String,
        @Field("password") password:String
    ): Call<Model>

    @GET("productview.php")
    fun getdata() : Call<List<ProductModel>>

    @FormUrlEncoded
    @POST("productupdate.php")
    fun updatedata
                (
        @Field("id") id:Int,
        @Field("product_name") product_name:String,
        @Field("product_price") product_price:String,
        @Field("product_description") product_description:String
    ): Call<Void>

    @FormUrlEncoded
    @POST("productdelete.php")
    fun deletedata
                (
        @Field("id") id:Int,
    ): Call<Void>

}