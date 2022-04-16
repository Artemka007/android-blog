package com.example.myapp.blog.network

import com.example.myapp.blog.data.PostCreateModel
import com.example.myapp.blog.data.PostModel
import com.example.myapp.blog.data.PostResponse
import com.example.myapp.blog.data.PostsResponse
import retrofit2.Call
import retrofit2.http.*

interface IBlogClient {
    @GET("posts/")
    fun geAllPosts(
        @Header("Authorization") token: String,
        @Query("context") context: String?
    ): Call<PostsResponse>

    @POST("posts/")
    fun sendPost(
        @Header("Authorization") token: String,
        @Body body: String
    ): Call<PostsResponse>

    @PUT("posts/like/")
    fun likePost(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Call<PostResponse>
}