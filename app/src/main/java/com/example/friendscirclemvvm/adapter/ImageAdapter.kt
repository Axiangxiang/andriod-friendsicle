package com.example.friendscirclemvvm.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.friendscirclemvvm.R

object ImageAdapter {

    @BindingAdapter("remote")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(it)
                .apply(RequestOptions().error(R.mipmap.ic_launcher))
                .into(imageView)
        }
    }
}