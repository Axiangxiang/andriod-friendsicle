package com.example.friendscirclemvvm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.friendscirclemvvm.model.Tweet
import com.example.friendscirclemvvm.repository.TweetRepository

class TweetViewModel(application: Application, context: Context) : AndroidViewModel(application) {
    private val tweetRepository = TweetRepository(context)
    val tweetLiveData = MutableLiveData<List<Tweet>>()

    fun getTweets(start: Long, end: Long) {
        tweetRepository.getTweets(start, end).let {
            tweetLiveData.postValue(it)
        }
    }
}