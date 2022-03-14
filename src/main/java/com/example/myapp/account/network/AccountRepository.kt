package com.example.myapp.account.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp.account.components.login.data.LoginResponse
import com.example.myapp.account.components.login.data.LoginModel
import com.example.myapp.account.data.AccountResponse
import com.example.myapp.blog.data.PostsResponse
import com.example.myapp.blog.network.IBlogClient
import com.example.myapp.network.ApiClient
import com.example.myapp.utils.ApiResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AccountRepository {
    private var apiClient: IAccountClient = ApiClient.getApiClient().create(IAccountClient::class.java)

    private var postClient: IBlogClient = ApiClient.getApiClient().create(IBlogClient::class.java)

    fun loginUser(loginModel: LoginModel): LiveData<LoginResponse?> {
        val res = ApiResponseUtil<LoginResponse?>()

        apiClient.loginUser(loginModel).enqueue(res.getCallback())

        return res.data
    }

    fun getProfile(token: String): LiveData<AccountResponse?> {
        val res = ApiResponseUtil<AccountResponse?>()

        apiClient.getAccountInfo(token).enqueue(res.getCallback())

        return res.data
    }

    fun getUserPosts(token: String): LiveData<PostsResponse?> {
        val res = ApiResponseUtil<PostsResponse?>()

        postClient.geAllPosts(token, "user").enqueue(res.getCallback())

        return res.data
    }
}