package com.example.myapp.network

import java.util.*

interface IResponse {
    val code: Int
    val message: String
    val errors: Dictionary<String, String>?
    val data: Any?
}