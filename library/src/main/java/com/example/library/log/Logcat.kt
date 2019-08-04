package com.example.library.log

import android.app.Activity
import android.app.Service
import android.os.Bundle
import android.view.MenuItem
import android.view.View

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
interface Logcat {

    /**
     * 控件点击事件埋点
     */
    fun holderOnClick(v: View)

    /**
     * 控件长按事件埋点
     */
    fun holderOnLongClick(v: View)

    /**
     *
     */
    fun holderOnMenuItemSelected(featureId: Int, item: MenuItem?)

    /**
     * Activity生命周期埋点 -> onCreate
     */
    fun holderActivityOnCreated(activity: Activity?)

    /**
     * Activity生命周期埋点 -> onStart
     */
    fun holderActivityOnStarted(activity: Activity?)

    /**
     * Activity生命周期埋点 -> onResume
     */
    fun holderActivityOnResumed(activity: Activity?)

    /**
     * Activity生命周期埋点 -> onPause
     */
    fun holderActivityOnPaused(activity: Activity?)

    /**
     * Activity生命周期埋点 -> onStop
     */
    fun holderActivityOnStopped(activity: Activity?)

    /**
     * Activity生命周期埋点 -> onDestroy
     */
    fun holderActivityOnDestroyed(activity: Activity?)

    /**
     * Activity生命周期埋点 -> onSaveInstanceState
     */
    fun holderActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?)

    fun holderServiceOnCreated(service: Service)

    fun holderServiceOnDestroyed(service: Service)
}