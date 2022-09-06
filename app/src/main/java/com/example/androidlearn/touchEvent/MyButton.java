package com.example.androidlearn.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class MyButton extends AppCompatButton {

    private static final String TAG = MyButton.class.getSimpleName();

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent: 事件开始分发");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: 按下");
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public MyButton(@NonNull Context context) {
        super(context);
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
