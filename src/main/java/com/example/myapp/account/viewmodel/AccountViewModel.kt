package com.example.myapp.account.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp.account.data.AccountResponse
import com.example.myapp.account.network.AccountRepository
import com.example.myapp.blog.data.PostsResponse
import com.example.myapp.blog.network.BlogRepository

class AccountViewModel (
    application: Application
): AndroidViewModel(application) {
    var accountLiveData : LiveData<AccountResponse?> = MutableLiveData()
    var blogLiveData : LiveData<PostsResponse?> = MutableLiveData()
    private var accountRepository: AccountRepository = AccountRepository()

    fun getProfile(token: String) {
        accountLiveData = accountRepository.getProfile(token)
    }

    fun getProfilePosts(token: String) {
        blogLiveData = accountRepository.getUserPosts(token)
    }
}