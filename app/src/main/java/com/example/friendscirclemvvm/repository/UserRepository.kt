package com.example.friendscirclemvvm.repository

import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.friendscirclemvvm.api.ApiClient
import com.example.friendscirclemvvm.model.User
import com.example.friendscirclemvvm.utils.FileUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class UserRepository(context: Context) {

    private val userInfoFileName = "user"

    val fileUtil = FileUtil(context)

    fun fetchAndSaveUserInfo() {
        val call = ApiClient.client.newCall(
            Request.Builder()
                .url(ApiClient.USER_URL)
                .get()
                .build()
        )

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("load user data fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val user = JSON.parseObject(response.body?.string(), User::class.java)
                fileUtil.saveToFile(userInfoFileName, user)
            }

        })
    }

    fun getUserInfo() : User? {
        return fileUtil.readFileToObject(userInfoFileName, User::class.java)
    }
}