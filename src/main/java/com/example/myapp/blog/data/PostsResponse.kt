package com.example.myapp.blog.data

import com.example.myapp.network.IResponse
import java.util.*

data class PostsResponse(
    override val code: Int,
    override val message: String,
    override val errors: Dictionary<String, String>?,
    override val data: List<PostModel>?
): IResponse
