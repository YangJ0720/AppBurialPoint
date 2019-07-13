package com.example.point

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


/**
 * @author YangJ
 */
class MyService : Service() {

    companion object {
        const val TAG = "MyService"
    }

    private val mBinder = MyBinder()

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind")
        return mBinder
    }

    private class MyBinder : Binder()

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }
}
