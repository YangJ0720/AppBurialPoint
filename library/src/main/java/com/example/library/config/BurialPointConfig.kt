package com.example.library.config

import android.content.Context
import com.example.library.log.DefaultLogcat
import com.example.library.log.Logcat

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
class BurialPointConfig {

    companion object {
        private var sLogcat: Logcat? = null

    }

    fun getLogcat(): Logcat? {
        return sLogcat
    }

    fun setLogcat(logcat: Logcat?) {
        sLogcat = logcat
    }

    class Builder {

        private var mContext: Context
        private var mLogcat: Logcat? = null

        constructor(context: Context) {
            this.mContext = context
        }

        fun setLogcat(logcat: Logcat): Builder {
            mLogcat = logcat
            return this
        }

        fun build(): BurialPointConfig {
            val config = BurialPointConfig()
            if (mLogcat == null) {
                mLogcat = DefaultLogcat(mContext)
            }
            config.setLogcat(mLogcat)
            return config
        }
    }

}