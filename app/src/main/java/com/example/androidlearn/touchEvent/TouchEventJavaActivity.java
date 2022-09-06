package com.example.androidlearn.touchEvent;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

public class TouchEventJavaActivity extends AppCompatActivity {

    private static final String TAG = TouchEventJavaActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_touch_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, "dispatchTouchEvent:按下");
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.i(TAG, "dispatchTouchEvent:抬起");
        }
        //这里是仿照源码的格式写的
        /**
         * 其实下面的代码就相当于
         * if(viewgroup.DispatchTouchEvent(ev)){
         *     return true;
         * }
         * 在这里判断了Activity的下一级的viewgroup是否拦截，而决定本级处理还是往下分发
         */
        if(getWindow().superDispatchTouchEvent(ev)){
            Log.i(TAG, "dispatchTouchEvent: 这里被调用");
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, "onTouchEvent:按下");
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i(TAG, "onTouchEvent:抬起");
        }
        return super.onTouchEvent(event);
    }
}
