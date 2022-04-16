package com.example.myapp.account.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp.account.data.AccountResponse
import com.example.myapp.account.network.AccountRepository
import com.example.myapp.blog.data.PostCreateModel
import com.example.myapp.blog.data.PostModel
import com.example.myapp.blog.data.PostsResponse
import com.example.myapp.blog.network.BlogRepository

class AccountViewModel (
    application: Application
): AndroidViewModel(application) {
    var accountLiveData : LiveData<AccountResponse?> = MutableLiveData()
    var blogLiveData : LiveData<PostsResponse?> = MutableLiveData()
    var createPostLiveData : LiveData<PostsResponse?> = MutableLiveData()

    private var accountRepository: AccountRepository = AccountRepository()
    private var blogRepository: BlogRepository = BlogRepository()

    fun getProfile(token: String) {
        accountLiveData = accountRepository.getProfile(token)
    }

    fun sendPost(token: String, body: String) {
        createPostLiveData = blogRepository.createPost(token, body)
    }

    fun getProfilePosts(token: String) {
        blogLiveData = accountRepository.getUserPosts(token)
    }
}