package com.example.androidlearn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 安卓 BroadcastReceiver
 */
public class BroadcastReceiverJavaActivity extends AppCompatActivity {

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        broadcastReceiver();
    }

    private void broadcastReceiver() {

        // 构建过滤器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_WALLPAPER_CHANGED);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SET_WALLPAPER.equals(intent.getAction())) {
                System.out.println("onReceive:" + intent.getAction());
                Toast.makeText(context, "你更换了壁纸，被我看到了！！", Toast.LENGTH_SHORT).show();
            }

            System.out.println("onReceive:" + intent.getAction());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
