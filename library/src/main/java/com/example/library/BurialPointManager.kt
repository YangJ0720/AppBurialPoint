package com.example.library

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.collection.ArraySet
import com.example.library.callback.WindowCallback
import com.example.library.config.BurialPointConfig
import com.example.library.log.Logcat

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
object BurialPointManager {

    private lateinit var mConfig: BurialPointConfig
    private lateinit var mArray: ArraySet<Activity>

    fun initialize(config: BurialPointConfig) {
        this.mConfig = config
        this.mArray = ArraySet()
    }

    fun getLogcat(): Logcat? {
        return mConfig.getLogcat()
    }

    /**
     * 对Activity埋点
     */
    fun onHoldActivity(application: Application) {
        val callbacks = object : Application.ActivityLifecycleCallbacks {

            val logcat = mConfig.getLogcat()

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                logcat?.holderActivityOnCreated(activity)
                val window = activity?.window ?: return
                val callback = window.callback
                window.callback = WindowCallback(callback, mConfig.getLogcat())
            }

            override fun onActivityStarted(activity: Activity?) {
                logcat?.holderActivityOnStarted(activity)
            }

            override fun onActivityResumed(activity: Activity?) {
                logcat?.holderActivityOnResumed(activity)
                if (!mArray.contains(activity)) {
                    // val view = activity?.findViewById<View>(android.R.id.content) ?: return
                    val view = activity?.window?.decorView ?: return
                    onHoldViewClick(view)
                    mArray.add(activity)
                }
            }

            override fun onActivityPaused(activity: Activity?) {
                logcat?.holderActivityOnPaused(activity)
            }

            override fun onActivityStopped(activity: Activity?) {
                logcat?.holderActivityOnStopped(activity)
            }

            override fun onActivityDestroyed(activity: Activity?) {
                logcat?.holderActivityOnDestroyed(activity)
                if (mArray.contains(activity)) {
                    mArray.remove(activity)
                }
                //
                val window = activity?.window ?:return
                val callback = window.callback
                if (callback is WindowCallback) {
                    callback.setLogcat(null)
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                logcat?.holderActivityOnSaveInstanceState(activity, outState)
            }

        }
        application.registerActivityLifecycleCallbacks(callbacks)
    }

    /**
     * 对View埋点
     * <p>通过判断childView是否设置OnClickListener进行劫持</p>
     *
     * @param view 参数为View对象
     */
    private fun onHoldViewClick(view: View) {
        if (view is ViewGroup) {
            val childCount = view.childCount
            for (i in 0 until childCount) {
                val childView = view.getChildAt(i)
                HoldViewUtils.hold(childView)
                if (childView is ViewGroup) {
                    onHoldViewClick(childView)
                }
            }
        } else {
            HoldViewUtils.hold(view)
        }
    }

}