package com.example.myapp.account.components.login.data

import com.example.myapp.network.IResponse
import java.util.*

data class LoginResponse (
    override val code: Int,
    override val message: String,
    override val errors: Dictionary<String, String>?,
    override val data: LoginResponseData
): IResponse