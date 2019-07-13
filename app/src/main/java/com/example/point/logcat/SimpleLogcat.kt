package com.example.point.logcat

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.library.log.Logcat

/**
 * 功能描述 自定义一个简易的日志记录器，不做任何操作仅仅在控制台输出日志
 * @author YangJ
 * @since 2019/7/14
 */
class SimpleLogcat : Logcat {

    companion object {
        const val TAG = "SimpleLogcat"
    }

    override fun holderOnClick(v: View) {
        Log.i(TAG, "holderOnClick: ${v.id}")
    }

    override fun holderOnLongClick(v: View) {
        Log.i(TAG, "holderOnLongClick: ${v.id}")
    }

    override fun holderActivityOnCreated(activity: Activity?) {
        Log.i(TAG,"holderActivityOnCreated: $activity")
    }

    override fun holderActivityOnStarted(activity: Activity?) {
        Log.i(TAG,"holderActivityOnStarted: $activity")
    }

    override fun holderActivityOnResumed(activity: Activity?) {
        Log.i(TAG,"holderActivityOnResumed: $activity")
    }

    override fun holderActivityOnPaused(activity: Activity?) {
        Log.i(TAG,"holderActivityOnPaused: $activity")
    }

    override fun holderActivityOnStopped(activity: Activity?) {
        Log.i(TAG,"holderActivityOnStopped: $activity")
    }

    override fun holderActivityOnDestroyed(activity: Activity?) {
        Log.i(TAG,"holderActivityOnDestroyed: $activity")
    }

    override fun holderActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?) {
        Log.i(TAG,"holderActivityOnSaveInstanceState: $activity")
    }

}