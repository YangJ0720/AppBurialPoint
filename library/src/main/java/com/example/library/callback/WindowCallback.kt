package com.example.library.callback

import android.annotation.TargetApi
import android.os.Build
import android.view.*
import android.view.accessibility.AccessibilityEvent
import com.example.library.log.Logcat

/**
 * @author YangJ
 */
class WindowCallback : Window.Callback {

    private val mCallback: Window.Callback
    private var mLogcat: Logcat? = null

    constructor(callback: Window.Callback, logcat: Logcat?) {
        this.mCallback = callback
        this.mLogcat = logcat
    }

    fun setLogcat(logcat: Logcat?) {
        mLogcat = logcat
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        mCallback.onActionModeFinished(mode)
    }

    override fun onCreatePanelView(featureId: Int): View? {
        return mCallback.onCreatePanelView(featureId)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return mCallback.dispatchTouchEvent(event)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu?): Boolean {
        return mCallback.onCreatePanelMenu(featureId, menu)
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback?): ActionMode? {
        return mCallback.onWindowStartingActionMode(callback)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onWindowStartingActionMode(callback: ActionMode.Callback?, type: Int): ActionMode? {
        return mCallback.onWindowStartingActionMode(callback, type)
    }

    override fun onAttachedToWindow() {
        mCallback.onAttachedToWindow()
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent?): Boolean {
        return mCallback.dispatchGenericMotionEvent(event)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean {
        return mCallback.dispatchPopulateAccessibilityEvent(event)
    }

    override fun dispatchTrackballEvent(event: MotionEvent?): Boolean {
        return mCallback.dispatchTrackballEvent(event)
    }

    override fun dispatchKeyShortcutEvent(event: KeyEvent?): Boolean {
        return mCallback.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return mCallback.dispatchKeyEvent(event)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        return mCallback.onMenuOpened(featureId, menu)
    }

    override fun onPanelClosed(featureId: Int, menu: Menu?) {
        mCallback.onPanelClosed(featureId, menu)
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem?): Boolean {
        mLogcat?.holderOnMenuItemSelected(featureId, item)
        return mCallback.onMenuItemSelected(featureId, item)
    }

    override fun onDetachedFromWindow() {
        mCallback.onDetachedFromWindow()
    }

    override fun onPreparePanel(featureId: Int, view: View?, menu: Menu?): Boolean {
        return mCallback.onPreparePanel(featureId, view, menu)
    }

    override fun onWindowAttributesChanged(attrs: WindowManager.LayoutParams?) {
        mCallback.onWindowAttributesChanged(attrs)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        mCallback.onWindowFocusChanged(hasFocus)
    }

    override fun onContentChanged() {
        mCallback.onContentChanged()
    }

    override fun onSearchRequested(): Boolean {
        return mCallback.onSearchRequested()
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onSearchRequested(searchEvent: SearchEvent?): Boolean {
        return mCallback.onSearchRequested(searchEvent)
    }

    override fun onActionModeStarted(mode: ActionMode?) {
        mCallback.onActionModeStarted(mode)
    }

}