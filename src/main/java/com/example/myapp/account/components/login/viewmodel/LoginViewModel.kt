package com.example.myapp.account.components.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp.account.network.AccountRepository
import com.example.myapp.account.components.login.data.LoginResponse
import com.example.myapp.account.components.login.data.LoginModel

class LoginViewModel (
    application: Application
): AndroidViewModel(application) {
    public var loginModelLiveData : LiveData<LoginResponse?> = MutableLiveData()
    private var accountRepository: AccountRepository = AccountRepository()

    fun loginUser(loginModel: LoginModel) {
        loginModelLiveData = accountRepository.loginUser(loginModel)
    }
}