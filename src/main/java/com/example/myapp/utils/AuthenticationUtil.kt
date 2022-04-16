package com.example.myapp.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class AuthenticationUtil {
    companion object {
        fun getToken(context: Context): String? {
            val file = File(context.filesDir, "token.txt")

            if (!file.exists()) return null

            val publicKey = readPublicKey(context)

            val token = file.readText()
            val key = SecurityUtil.loadPublicKey(publicKey)

            return SecurityUtil.encryptByPublicKey(token, key)
        }

        private fun readPublicKey(context: Context): String {
            var mReader: BufferedReader? = null
            var mResult: String = ""
            var mLine: String? = ""

            try {
                mReader = BufferedReader(InputStreamReader(context.assets.open("public_key.pem")))
                while (mLine != null) {
                    mLine = mReader.readLine()
                    if (mLine != null) mResult += mLine
                }
            } catch (e: IOException) {
                Log.d("ERROR", e.message.toString())
            } finally {
                mReader?.close()
            }

            return mResult
        }
    }
}