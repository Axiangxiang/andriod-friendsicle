package com.example.friendscirclemvvm.api

import okhttp3.OkHttpClient

object ApiClient {
    const val USER_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith"
    const val TWEET_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets"

    val client by lazy(LazyThreadSafetyMode.NONE) { OkHttpClient.Builder().build() }
}