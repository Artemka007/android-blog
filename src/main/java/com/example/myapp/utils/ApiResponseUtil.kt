package com.example.myapp.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapp.blog.data.PostsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResponseUtil<T> {
    var data = MutableLiveData<T?>()

    fun getCallback(): Callback<T> {
        return object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                val message = t.message
                if (message is String) {
                    Log.d("ERROR", message)
                } else {
                    Log.d("ERROR", "Something went wrong.")
                }
                data.value = null
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                data.value = response.body()
            }
        }
    }
}