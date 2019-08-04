package com.example.library.log

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * 功能描述 默认日志记录器，会根据日期作为文本名称并将日志写入SDCard
 * @author YangJ
 * @since 2019/7/14
 */
open class DefaultLogcat : Logcat {

    companion object {
        private const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
        private const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        private const val FILE_FORMAT_TXT = ".txt"
    }

    private var mExternalCachePath: String

    constructor(context: Context) {
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val sb = StringBuilder(cacheDir.absolutePath)
        sb.append(File.separator)
        sb.append(getFileName())
        sb.append(FILE_FORMAT_TXT)
        mExternalCachePath = sb.toString()
    }

    override fun holderOnClick(v: View) {
        val text = getTemplateText("OnClick: ${v.id}")
        write(text)
    }

    override fun holderOnLongClick(v: View) {
        val text = getTemplateText("OnLongClick: ${v.id}")
        write(text)
    }

    override fun holderOnMenuItemSelected(featureId: Int, item: MenuItem?) {
        val text = getTemplateText("OnMenuItemSelected: ${item?.itemId}")
        write(text)
    }

    override fun holderActivityOnCreated(activity: Activity?) {
        val text = getTemplateText("ActivityOnCreated: $activity")
        write(text)
    }

    override fun holderActivityOnStarted(activity: Activity?) {
        val text = getTemplateText("ActivityOnStarted: $activity")
        write(text)
    }

    override fun holderActivityOnResumed(activity: Activity?) {
        val text = getTemplateText("ActivityOnResumed: $activity")
        write(text)
    }

    override fun holderActivityOnPaused(activity: Activity?) {
        val text = getTemplateText("ActivityOnPaused: $activity")
        write(text)
    }

    override fun holderActivityOnStopped(activity: Activity?) {
        val text = getTemplateText("ActivityOnStopped: $activity")
        write(text)
    }

    override fun holderActivityOnDestroyed(activity: Activity?) {
        val text = getTemplateText("ActivityOnDestroyed: $activity")
        write(text)
    }

    override fun holderActivityOnSaveInstanceState(activity: Activity?, outState: Bundle?) {
        val text = getTemplateText("ActivityOnSaveInstanceState: $activity")
        write(text)
    }

    /**
     * 获取日志文件名称
     */
    private fun getFileName(): String {
        val pattern = DATE_FORMAT_YYYY_MM_DD
        return getDateFormat(pattern)
    }

    /**
     * 获取格式化日期
     */
    private fun getDateFormat(pattern: String): String {
        val format = SimpleDateFormat(pattern, Locale.CHINA)
        return format.format(Date())
    }

    private fun getTemplateText(str: String): String {
        val sb = StringBuilder(str)
        sb.append("\r\n")
        sb.append(getDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS))
        sb.append("\r\n")
        return sb.toString()
    }

    /**
     * 将日志写入文件
     * @param text 参数为日志内容
     */
    private fun write(text: String) {
        val file = File(mExternalCachePath)
        // 判断日志文件是否存在
        if (!file.exists()) {
            file.createNewFile()
        }
        // 开始写入
        var inputStream: ByteArrayInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            val bytes = text.toByteArray()
            inputStream = ByteArrayInputStream(bytes, 0, bytes.size)
            fileOutputStream = FileOutputStream(file, true)
            val buffer = ByteArray(1024)
            while (true) {
                val len = inputStream.read(buffer)
                if (len == -1) {
                    break
                }
                fileOutputStream.write(buffer, 0, len)
            }
        } finally {
            inputStream?.close()
            fileOutputStream?.close()
        }
    }

}