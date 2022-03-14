package com.example.myapp.utils

import android.content.Context
import java.io.File

class AuthenticationUtil {
    companion object {
        fun getToken(context: Context): String? {
            val file = File(context.filesDir, "token.txt")
            if (!file.exists()) return null
            return file.readText()
        }
    }
}