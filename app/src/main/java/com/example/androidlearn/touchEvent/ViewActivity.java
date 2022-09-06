package com.example.androidlearn.touchEvent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = ViewActivity.class.getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);

        MyButton button = findViewById(R.id.mButton);
        button.setOnClickListener(view -> {
            Log.i(TAG, "onClick:");
        });

        /**
         * 决定View拦击不拦截的核心方法
         * onTouch
         * true 表示消费该事件，不调用View自身的onTouchEvent、onClick
         * false 表示不消费该事件，调用View自身的onTouchEvent、onClick
         *
         */
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
}
