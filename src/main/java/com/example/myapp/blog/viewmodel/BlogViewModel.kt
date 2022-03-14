package com.example.myapp.blog.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp.blog.data.PostResponse
import com.example.myapp.blog.data.PostsResponse
import com.example.myapp.blog.network.BlogRepository

class BlogViewModel (
    application: Application
): AndroidViewModel(application)  {
    public var blogLiveData: LiveData<PostsResponse?> = MutableLiveData()
    private var blogRepository: BlogRepository = BlogRepository()

    public var postLiveData: LiveData<PostResponse?> = MutableLiveData()

    fun getAllPosts(token: String) {
        blogLiveData = blogRepository.getAllPosts(token, null)
    }

    fun likePost(token: String, id: Int) {
        postLiveData = blogRepository.likePost(token, id)
    }
}