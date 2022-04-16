package com.example.myapp.blog.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp.blog.data.PostCreateModel
import com.example.myapp.blog.data.PostModel
import com.example.myapp.blog.data.PostResponse
import com.example.myapp.blog.data.PostsResponse
import com.example.myapp.network.ApiClient
import com.example.myapp.utils.ApiResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogRepository {
    private var apiClient = ApiClient.getApiClient().create(IBlogClient::class.java)

    fun getAllPosts(token: String, context: String?): LiveData<PostsResponse?> {
        val res = ApiResponseUtil<PostsResponse?>()

        apiClient.geAllPosts(token, context).enqueue(res.getCallback())

        return res.data
    }

    fun createPost(token: String, body: String): LiveData<PostsResponse?> {
        val res = ApiResponseUtil<PostsResponse?>()

        apiClient.sendPost(token, body).enqueue(res.getCallback())

        return res.data
    }

    fun likePost(token: String, id: Int): LiveData<PostResponse?> {
        val res = ApiResponseUtil<PostResponse?>()

        apiClient.likePost(token, id).enqueue(res.getCallback())

        return res.data
    }
}