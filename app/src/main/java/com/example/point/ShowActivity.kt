package com.example.point

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder

import androidx.appcompat.app.AppCompatActivity

/**
 * @author YangJ
 */
class ShowActivity : AppCompatActivity() {

    private lateinit var mConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        initService()
    }

    private fun initService() {
        val intent = Intent(this, MyService::class.java)
        mConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                println("onServiceDisconnected: $name")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                println("onServiceConnected: $name")
            }

        }
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }
}
