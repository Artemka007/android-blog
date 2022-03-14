package com.example.myapp.account.network

import com.example.myapp.account.components.login.data.LoginResponse
import com.example.myapp.account.components.login.data.LoginModel
import com.example.myapp.account.data.AccountResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header

interface IAccountClient {
    @GET("account/")
    fun getAccountInfo(@Header("Authorization") token: String): Call<AccountResponse>
    @POST("account/login/")
    fun loginUser(@Body loginModel: LoginModel): Call<LoginResponse>
}