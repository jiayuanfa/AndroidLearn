package com.example.androidlearn.architecture;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * 架构模式
 * MVC
 * MVP
 * MVVM
 * MVI
 */
public class ArchitectureJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);
    }
}
