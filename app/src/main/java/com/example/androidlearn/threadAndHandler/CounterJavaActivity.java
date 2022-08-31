package com.example.androidlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.androidlearn.dataSave.SharePreferencesJavaActivity;

/**
 * Handler使用 倒计时java版本
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * 新线程中，发送Message消息给Handler处理，表示自己是哪一条线的
     * @param savedInstanceState
     */
    private MyHandler myHandler = new MyHandler();

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.mCounterTv);

        mTextView.setOnClickListener(view -> {
//            startActivity(new Intent(this, Camera2JavaActivity.class));   // 相机
            startActivity(new Intent(this, SharePreferencesJavaActivity.class));    // SP
        });
    }

    private void thread() {

        mTextView = findViewById(R.id.mCounterTv);

        new Thread() {

            int time = 30;

            @Override
            public void run() {
                super.run();

                while (time >= 0) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = time;
                    message.obj = "来自子线程的发哥";
                    myHandler.sendMessage(message);
                    time--;
                }
            }
        }.start();
    }

    /**
     * 一：在主线程中创建一个Handler，表示让主线程干点什么
     */
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // 这里可以操作UI控件
            Log.d(TAG, msg.obj.toString());

            mTextView.setText(String.valueOf(msg.what));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
        myHandler = null;
    }
}