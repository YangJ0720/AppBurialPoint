package com.example.library.view

import android.view.View
import com.example.library.BurialPointManager

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
class HoldOnLongClickListener : View.OnLongClickListener {

    private val mListener: View.OnLongClickListener

    constructor(listener: View.OnLongClickListener) {
        mListener = listener
    }

    override fun onLongClick(v: View): Boolean {
        println("onLongClick: ${v.id}")
        BurialPointManager.getLogcat()?.holderOnLongClick(v)
        return mListener.onLongClick(v)
    }

}