package com.example.friendscirclemvvm.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.alibaba.fastjson.JSON
import com.example.friendscirclemvvm.model.Tweet
import com.example.friendscirclemvvm.model.User
import com.example.friendscirclemvvm.repository.TweetRepository
import com.example.friendscirclemvvm.repository.UserRepository
import com.example.friendscirclemvvm.utils.FileUtil
import okhttp3.*
import java.io.IOException

class LoadDataService : Service() {

    val userRepository = UserRepository(this)

    val tweetRepository = TweetRepository(this)

    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }

    inner class MyBinder: Binder() {

        fun loadProfileData() {
            userRepository.fetchAndSaveUserInfo()
        }

        fun loadTweetsData() {
            tweetRepository.fetchAndSaveTweets()
        }
    }

}
