package com.example.library.view;

import android.view.View;

import com.example.library.BurialPointManager;

/**
 * 功能描述
 *
 * @author YangJ
 * @since 2019/7/14
 */
public class HoldOnClickListener implements View.OnClickListener {

    private View.OnClickListener mListener;

    public HoldOnClickListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        System.out.println("onClick: " + v.getId());
        BurialPointManager.getInstance().getLogcat().holderOnClick(v);
        mListener.onClick(v);
    }

}
