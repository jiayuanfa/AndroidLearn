package com.example.androidlearn.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 分析ViewGroup的事件分发逻辑
 */
public class MyLayout extends LinearLayout {

    private static final String TAG = MyLayout.class.getSimpleName();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 决定其往下分发还是不分发的核心关键方法
     * 返回 false 表示不拦截，往下分发事件，也就是内部的View去子类响应
     * 返回 true 表示拦截，不往下分发事件，也就是本身去响应事件, 本身的onTouchEvent被调用
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "onTouchEvent:");
        return super.onTouchEvent(event);
    }

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
