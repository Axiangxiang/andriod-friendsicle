package com.example.friendscirclemvvm.adapter

import android.content.Context
import android.util.JsonReader
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.example.friendscirclemvvm.R
import com.example.friendscirclemvvm.databinding.TweetItemBinding
import com.example.friendscirclemvvm.databinding.UserInfoBinding
import com.example.friendscirclemvvm.model.Tweet
import com.example.friendscirclemvvm.model.User

class TweetAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val tweets = mutableListOf<Tweet>()
    private var user: User? = null

    private val typeHeader = 1

    private val typeItem = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            typeHeader -> {
                val dataBinding = DataBindingUtil.inflate<UserInfoBinding>(LayoutInflater.from(parent.context), R.layout.user_info, parent,false)
                HeaderViewHolder(dataBinding)
            }
            else -> {
                val dataBinding = DataBindingUtil.inflate<TweetItemBinding>(LayoutInflater.from(parent.context), R.layout.tweet_item, parent,false)
                ItemViewHolder(dataBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return tweets.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            println("ItemViewHolder position: $position")
            val tweet = tweets[position - 1]
            holder.bind(tweet)

            if (tweet.comments != null) {
                for (comment in tweet.comments!!) {
                    val nickTextView = TextView(context);
                    nickTextView.text = "${comment.sender?.nick}: "
                    nickTextView.textSize = 18f
                    holder.commentLayout.addView(nickTextView)

                    val contentTextView = TextView(context);
                    contentTextView.text = comment.content
                    contentTextView.textSize = 18f
                    holder.commentLayout.addView(contentTextView)
                }
            }

        } else if (holder is HeaderViewHolder) {
            println("HeaderViewHolder position: $position")
            println("HeaderViewHolder user: ${JSON.toJSONString(user)}")
            user?.let {
                holder.bind(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> {
                typeHeader
            }
            else -> {
                typeItem
            }
        }
    }

    fun updateHeader(newData: User?) {
        println("user newData: ${JSON.toJSONString(newData)}")
        newData?.let {
            this.user = newData
            notifyDataSetChanged()
        }
    }

    fun updateData(newData: List<Tweet>?) {
        newData?.let {
            tweets.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class HeaderViewHolder(private val dataBinding: UserInfoBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(user: User) {
            dataBinding.user = user
        }
    }

    inner class ItemViewHolder(private val dataBinding: TweetItemBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        val commentLayout = dataBinding.root.findViewById<LinearLayout>(R.id.comment_layout)
        fun bind(tweet: Tweet) {
            dataBinding.tweet = tweet
        }
    }
}