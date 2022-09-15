package com.example.androidlearn.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.androidlearn.R;

/**
 * 使用LayoutInflater来加载布局
 */
public class LayoutInflaterJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_inflater);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View button = layoutInflater.inflate(R.layout.button_layout, null);

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.addView(button);
    }
}
