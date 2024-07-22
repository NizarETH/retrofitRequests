package com.app.retrofitrequests

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {

    @GET("objects")
    fun getObjects(): Call<MutableList<Object>>
    @GET("objects/{id}")
    fun getObjectsById(@Path("id") id: String): Call<Object>
    @POST("objects")
    fun createObject(@Body data: Object): Call<Object>
    @PUT("objects/{id}")
    fun updateObject(@Path("id") id: String, @Body data: Object): Call<Object>
    @DELETE("objects/{id}")
    fun deleteObject(@Path("id") id: String): Call<Void>
}