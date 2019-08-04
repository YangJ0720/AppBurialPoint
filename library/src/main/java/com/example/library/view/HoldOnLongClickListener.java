package com.example.library.view;

import android.view.View;

import com.example.library.BurialPointManager;

/**
 * 功能描述
 *
 * @author YangJ
 * @since 2019/7/14
 */
public class HoldOnLongClickListener implements View.OnLongClickListener {

    private View.OnLongClickListener mListener;

    public HoldOnLongClickListener(View.OnLongClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        System.out.println("onLongClick: " + v.getId());
        BurialPointManager.getInstance().getLogcat().holderOnLongClick(v);
        return mListener.onLongClick(v);
    }
}
