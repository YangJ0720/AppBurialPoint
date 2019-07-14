package com.example.library

import android.view.View
import com.example.library.view.HoldOnClickListener
import com.example.library.view.HoldOnLongClickListener

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
object HoldViewUtils {

    private const val NAME_GET_LISTENER_INFO = "getListenerInfo"
    private const val NAME_M_ON_CLICK_LISTENER = "mOnClickListener"
    private const val NAME_M_ON_LONG_CLICK_LISTENER = "mOnLongClickListener"

    fun hold(view: View) {
        println("view = $view")
        // 获取View的class对象
        val cls = View::class.java
        // 反射获取View的getListenerInfo方法
        val method = cls.getDeclaredMethod(NAME_GET_LISTENER_INFO)
        method.isAccessible = true
        // 通过调用getListenerInfo方法获取mListenerInfo对象
        val mListenerInfo = method.invoke(view)
        // 获取mListenerInfo对象的class对象
        val clsListenerInfo = mListenerInfo.javaClass
        // 设置OnClickListener埋点
        holdOnClickListener(view, clsListenerInfo, mListenerInfo)
        // 设置OnLongClickListener埋点
        holdOnLongClickListener(view, clsListenerInfo, mListenerInfo)
    }

    /**
     * 设置OnClickListener埋点
     */
    private fun holdOnClickListener(view: View, clsListenerInfo: Class<Any>, mListenerInfo: Any) {
        // 获取静态内部类ListenerInfo的成员变量mOnClickListener
        val field = clsListenerInfo.getField(NAME_M_ON_CLICK_LISTENER)
        // 获取mOnClickListener对象
        val mOnClickListener = field.get(mListenerInfo)
        // 通过判断mOnClickListener是否为空可知是否设置点击事件
        if (mOnClickListener != null && mOnClickListener is View.OnClickListener) {
            // 对设置点击事件的View重新设置自定义代理点击事件
            view.setOnClickListener(HoldOnClickListener(mOnClickListener))
        }
    }

    /**
     * 设置OnLongClickListener埋点
     */
    private fun holdOnLongClickListener(view: View, clsListenerInfo: Class<Any>, mListenerInfo: Any) {
        // 获取静态内部类ListenerInfo的成员变量mOnLongClickListener
        val field = clsListenerInfo.getDeclaredField(NAME_M_ON_LONG_CLICK_LISTENER)
        field.isAccessible = true
        // 获取mOnLongClickListener对象
        val mOnLongClickListener = field.get(mListenerInfo)
        // 通过判断mOnLongClickListener是否为空可知是否设置点击事件
        if (mOnLongClickListener != null && mOnLongClickListener is View.OnLongClickListener) {
            // 对设置点击事件的View重新设置自定义代理长按事件
            view.setOnLongClickListener(HoldOnLongClickListener(mOnLongClickListener))
        }
    }

}