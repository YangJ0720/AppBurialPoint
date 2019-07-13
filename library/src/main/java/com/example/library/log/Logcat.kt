package com.example.library.log

import android.app.Activity
import android.os.Bundle
import android.view.View

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
interface Logcat {

    fun holderOnClick(v: View)

    fun holderOnLongClick(v: View)

    fun holderActivityOnCreated(activity: Activity?)

    fun holderActivityOnStarted(activity: Activity?)

    fun holderActivityOnResumed(activity: Activity?)

    fun holderActivityOnPaused(activity: Activity?)

    fun holderActivityOnStopped(activity: Activity?)

    fun holderActivityOnDestroyed(activity: Activity?)

    fun holderActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?)
}