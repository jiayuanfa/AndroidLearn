package com.example.androidlearn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone);

        findViewById(R.id.button).setOnClickListener(view -> {
            Intent intent = new Intent(this, PhoneCallJavaActivity.class);
            intent.putExtra("name", "张三");
            intent.putExtra("phoneNumber", "123456789");
            startActivity(intent);
        });
    }
}
