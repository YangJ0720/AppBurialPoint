package com.example.point

import android.app.Application
import com.example.library.BurialPointManager
import com.example.library.config.BurialPointConfig
import com.example.library.log.DefaultLogcat

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化日志记录器
        // val logcat = SimpleLogcat()
        val logcat = DefaultLogcat(this)
        val config = BurialPointConfig.Builder(this).setLogcat(logcat).build()
        BurialPointManager.initialize(config)
        BurialPointManager.onHoldActivity(this)
    }
}