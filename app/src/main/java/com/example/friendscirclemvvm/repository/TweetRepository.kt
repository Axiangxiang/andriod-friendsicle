package com.example.friendscirclemvvm.repository

import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.friendscirclemvvm.api.ApiClient
import com.example.friendscirclemvvm.model.Tweet
import com.example.friendscirclemvvm.model.User
import com.example.friendscirclemvvm.utils.FileUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TweetRepository(context: Context) {

    private val tweetFileName = "tweets"

    private val fileUtil = FileUtil(context)

    fun fetchAndSaveTweets() {
        val call = ApiClient.client.newCall(
            Request.Builder()
                .url(ApiClient.TWEET_URL)
                .get()
                .build()
        )

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                println("load tweets data fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val sweets = JSON.parseArray(response.body?.string(), Tweet::class.java)
                val filter = sweets.filter { it.sender != null }
                if (fileUtil.exists(tweetFileName)) {
                    if (fileUtil.deleteFile(tweetFileName)) {
                        for (item in filter) {
                            fileUtil.appendToFileWithLine(tweetFileName, item)
                        }
                    }
                } else {
                    for (item in filter) {
                        fileUtil.appendToFileWithLine(tweetFileName, item)
                    }
                }
            }

        })
    }

    fun getTweets(start: Long, end: Long): List<Tweet> {
        return fileUtil.readFileByLines(tweetFileName, start, end, Tweet::class.java)
    }
}