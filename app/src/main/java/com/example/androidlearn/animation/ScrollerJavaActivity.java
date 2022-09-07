package com.example.androidlearn.animation;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * Scroller
 * 创建一个Scroller；
 * 重写 view 的 computeScroll 方法;
 * 然后通过 mScroller.startScroll()来实现滑动。
 */
public class ScrollerJavaActivity extends AppCompatActivity {

    private TestScroller testScroller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scroller);

        testScroller = findViewById(R.id.mTestScroller);

        testScroller.setOnClickListener(view -> {
            testScroller.smoothScrollTo(200, 200);
        });
    }
}
