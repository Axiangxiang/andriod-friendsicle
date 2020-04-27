package com.example.friendscirclemvvm.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.friendscirclemvvm.R
import com.example.friendscirclemvvm.service.LoadDataService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myBinder: LoadDataService.MyBinder

    private val myServiceConnection = MyServiceConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(Intent(this, LoadDataService::class.java), myServiceConnection, Context.BIND_AUTO_CREATE)

        btn_friends_circle.setOnClickListener {
            startActivity(Intent(this, FriendsCircleActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(myServiceConnection)
    }

    inner class MyServiceConnection: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myBinder = service as LoadDataService.MyBinder
            myBinder.loadProfileData()
            myBinder.loadTweetsData()
        }
    }
}
