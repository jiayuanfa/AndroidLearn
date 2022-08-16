package com.example.androidlearn;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/**
 * 抽奖系统Java版本
 */
public class LuckyDrawActivity extends AppCompatActivity {

    String[] names = new String[]{"优弧","船长","托尼","春哥","若川巨","南方佬"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lucky_draw);

        TextView resultTv = findViewById(R.id.mResultTv);
        findViewById(R.id.mLuckyDrawBtn).setOnClickListener(view -> {

            int i = new Random().nextInt(names.length);
            resultTv.setText(names[i]);
        });
    }
}
