package com.example.androidlearn.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {

    private static final String TAG = MyViewGroup.class.getSimpleName();

    /**
     * ViewGroup 中
     * 此方法返回True，意思是交给本ViewGroup处理OnTouchEvent事件
     * 返回false，表示不往下分发事件，由父级来响应点击事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
