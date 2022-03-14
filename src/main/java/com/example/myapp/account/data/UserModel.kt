package com.example.myapp.account.data

import com.example.myapp.blog.data.PostModel

data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val photo: String?,
    val posts: List<PostModel>?
) {
    fun getFullName(): String {
        return "$first_name $last_name"
    }
}
