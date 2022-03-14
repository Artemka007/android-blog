package com.example.myapp.account.data

import com.example.myapp.network.IResponse
import java.util.*

data class AccountResponse(
     override val code: Int,
     override val message: String,
     override val errors: Dictionary<String, String>?,
     override val data: UserModel?
): IResponse
