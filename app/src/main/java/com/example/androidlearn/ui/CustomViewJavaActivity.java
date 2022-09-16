package com.example.androidlearn.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * 自定义View的使用
 * 1: 自绘控件
 * 2：组合控件
 * 3：继承控件
 */
public class CustomViewJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_custom_view);
        setContentView(R.layout.activity_custom_view_self_invalidate);  // 自绘控件
    }
}
