package com.example.androidlearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneCallJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone_call);

        TextView name = findViewById(R.id.mTv);
        name.setText(getIntent().getStringExtra("name"));
        TextView phone = findViewById(R.id.textView11);
        phone.setText(getIntent().getStringExtra("phoneNumber"));

        findViewById(R.id.button2).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + getIntent().getStringExtra("phoneNumber")));
            startActivity(intent);
        });
    }
}
