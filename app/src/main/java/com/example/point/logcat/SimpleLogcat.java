package com.example.point.logcat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.library.log.Logcat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 功能描述 自定义一个简易的日志记录器，不做任何操作仅仅在控制台输出日志
 *
 * @author YangJ
 * @since 2019/7/14
 */
public class SimpleLogcat implements Logcat {

    // TAG
    private static final String TAG = "SimpleLogcat";

    @Override
    public void holderOnClick(@NotNull View v) {
        Log.i(TAG, "holderOnClick: " + v);
    }

    @Override
    public void holderOnLongClick(@NotNull View v) {
        Log.i(TAG, "holderOnLongClick: " + v);
    }

    @Override
    public void holderOnMenuItemSelected(int featureId, @Nullable MenuItem item) {
        Log.i(TAG, "holderOnMenuItemSelected: " + item.getTitle());
    }

    @Override
    public void holderActivityOnCreated(@Nullable Activity activity) {
        Log.i(TAG, "holderActivityOnCreated: " + activity);
    }

    @Override
    public void holderActivityOnStarted(@Nullable Activity activity) {
        Log.i(TAG, "holderActivityOnStarted: " + activity);
    }

    @Override
    public void holderActivityOnResumed(@Nullable Activity activity) {
        Log.i(TAG, "holderActivityOnResumed: " + activity);
    }

    @Override
    public void holderActivityOnPaused(@Nullable Activity activity) {
        Log.i(TAG, "holderActivityOnPaused: " + activity);
    }

    @Override
    public void holderActivityOnStopped(@Nullable Activity activity) {
        Log.i(TAG, "holderActivityOnStopped: " + activity);
    }

    @Override
    public void holderActivityOnDestroyed(@Nullable Activity activity) {
        Log.i(TAG, "holderActivityOnDestroyed: " + activity);
    }

    @Override
    public void holderActivityOnSaveInstanceState(@Nullable Activity activity, @Nullable Bundle outState) {
        Log.i(TAG, "holderActivityOnSaveInstanceState: " + activity);
    }
}
