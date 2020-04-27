package com.example.friendscirclemvvm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.friendscirclemvvm.model.User
import com.example.friendscirclemvvm.repository.UserRepository

class UserViewModel(application: Application, context: Context) : AndroidViewModel(application) {
    private val userRepository = UserRepository(context)
    val userLiveData = MutableLiveData<User>()

    fun getUserInfo() {
        userRepository.getUserInfo()?.let {
            userLiveData.postValue(it)
        }
    }
}