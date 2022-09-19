package com.example.androidlearn.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;
import com.example.androidlearn.ui.customView.NavigationBarView;

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
//        setContentView(R.layout.activity_custom_view_self_invalidate);  // 自绘控件
//        setContentView(R.layout.activity_custom_view_combined_control); // 组合控件
//        initView();

        setContentView(R.layout.activity_custom_view_inherited_contrl); // 继承控件
    }

    private void initView() {
        NavigationBarView navigationBarView = findViewById(R.id.mNavView);
        navigationBarView.setTitleText("组合控件");

        navigationBarView.setLeftButtonText("组合返回");

        // 重写自定义组合控件的返回方法
        navigationBarView.setLeftButtonListener(view -> {
            finish();
        });
    }
}
