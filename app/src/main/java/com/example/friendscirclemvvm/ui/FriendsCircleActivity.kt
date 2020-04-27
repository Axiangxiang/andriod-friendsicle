package com.example.friendscirclemvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.friendscirclemvvm.R
import com.example.friendscirclemvvm.adapter.TweetAdapter
import com.example.friendscirclemvvm.viewmodel.TweetViewModel
import com.example.friendscirclemvvm.viewmodel.UserViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_friends_circle.*

class FriendsCircleActivity : AppCompatActivity() {

    private val tweetAdapter by lazy { TweetAdapter(this) }

    private val tweetViewModel by lazy { TweetViewModel(this.application, this) }

    private var pageNumber = 0L

    private var pageSize = 5L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_circle)

        initTweetsView()
    }

    private fun initTweetsView() {
        val userViewModel = UserViewModel(this.application, this)
        userViewModel.userLiveData.observe(this, Observer { user -> tweetAdapter.updateHeader(user) })

        tweetViewModel.tweetLiveData.observe(this, Observer { tweets ->
            run {
                if (tweets.isNotEmpty()) {
                    tweetAdapter.updateData(tweets)
                }
            }
        })

        println("get user info")
        userViewModel.getUserInfo()

        val linearLayoutManager = LinearLayoutManager(this)
        val recyclerView = recycler_view as XRecyclerView
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = tweetAdapter

        recyclerView.setLoadingListener(MyLoadingListener(recyclerView))


        tweetViewModel.getTweets(pageNumber * pageSize, pageNumber * pageSize + pageSize)

        pageNumber += 1
    }

    inner class MyLoadingListener(private val recyclerView: XRecyclerView) : XRecyclerView.LoadingListener {
        override fun onLoadMore() {
            tweetViewModel.getTweets(pageNumber * pageSize, pageNumber * pageSize + pageSize)
            pageNumber += 1
            recyclerView.loadMoreComplete()
        }

        override fun onRefresh() {

        }

    }

}
