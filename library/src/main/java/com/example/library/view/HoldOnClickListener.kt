package com.example.library.view

import android.view.View
import com.example.library.BurialPointManager

/**
 * 功能描述
 * @author YangJ
 * @since 2019/7/14
 */
class HoldOnClickListener : View.OnClickListener {

    private var mListener: View.OnClickListener

    constructor(listener: View.OnClickListener) {
        mListener = listener
    }

    override fun onClick(v: View) {
        println("onClick: ${v.id}")
        BurialPointManager.getLogcat()?.holderOnClick(v)
        mListener.onClick(v)
    }

}