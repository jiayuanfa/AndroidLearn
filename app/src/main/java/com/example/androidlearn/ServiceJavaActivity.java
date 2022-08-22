package com.example.androidlearn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.service.MyService;

/**
 * Service 读唐诗
 */
public class ServiceJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service);

        Intent intent = new Intent(this, MyService.class);

        // 启动服务
        findViewById(R.id.button3).setOnClickListener(view -> {
            startService(intent);
        });

        // 停止服务
        findViewById(R.id.button4).setOnClickListener(view -> {
            stopService(intent);
        });
    }
}
