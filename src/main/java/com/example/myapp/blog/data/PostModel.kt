package com.example.myapp.blog.data

import com.example.myapp.account.data.UserModel

data class PostModel(
    val id: Int,
    val user: UserModel,
    val body: String,
    val created_time: String,
    val updated_time: String,
    val likes: List<UserModel>
) {
    override fun equals(other: Any?): Boolean {
        return other is PostModel && other.id == this.id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + user.id
        result = 31 * result + body.hashCode()
        result = 31 * result + created_time.hashCode()
        result = 31 * result + updated_time.hashCode()
        return result
    }
}
