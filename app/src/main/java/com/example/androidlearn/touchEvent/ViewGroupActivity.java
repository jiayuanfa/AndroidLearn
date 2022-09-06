package com.example.androidlearn.touchEvent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

public class ViewGroupActivity extends AppCompatActivity {

    private static final String TAG = ViewGroupActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_group);

        findViewById(R.id.mButton).setOnClickListener(view -> {
            Log.i(TAG, "按钮被点击了");
        });
    }
}
