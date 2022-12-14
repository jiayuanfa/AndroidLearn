package com.example.androidlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.androidlearn.animation.AnimationJavaActivity;
import com.example.androidlearn.animation.ChangeLayoutParamsJavaActivity;
import com.example.androidlearn.animation.RotateAnimationJavaActivity;
import com.example.androidlearn.animation.ScrollConflictJavaActivity;
import com.example.androidlearn.animation.ScrollerJavaActivity;
import com.example.androidlearn.animation.ViewScrollJavaActivity;
import com.example.androidlearn.architecture.mvc.MvcActivity;
import com.example.androidlearn.architecture.mvp.MvpActivity;
import com.example.androidlearn.architecture.mvvm.MvvmActivity;
import com.example.androidlearn.dataSave.LruCacheJavaActivity;
import com.example.androidlearn.dataSave.SharePreferencesJavaActivity;
import com.example.androidlearn.hardware.SensorDirectionJavaActivity;
import com.example.androidlearn.hardware.SensorJavaActivity;
import com.example.androidlearn.network.NetworkJavaActivity;
import com.example.androidlearn.network.OkHttpActivity;
import com.example.androidlearn.network.RetrofitActivity;
import com.example.androidlearn.network.glide.Glide4Activity;
import com.example.androidlearn.network.glide.GlideActivity;
import com.example.androidlearn.network.glide.GlideCustomTargetActivity;
import com.example.androidlearn.rxjava.RxJavaActivity;
import com.example.androidlearn.touchEvent.TouchEventJavaActivity;
import com.example.androidlearn.touchEvent.ViewActivity;
import com.example.androidlearn.touchEvent.ViewGroupActivity;
import com.example.androidlearn.ui.CanvasCustomViewJavaActivity;
import com.example.androidlearn.ui.CustomViewJavaActivity;
import com.example.androidlearn.ui.LayoutInflaterJavaActivity;
import com.example.androidlearn.ui.LinkRecycleViewJavaActivity;
import com.example.androidlearn.ui.ViewStatusJavaActivity;

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
//            startActivity(new Intent(this, SharePreferencesJavaActivity.class));    // SP
//            startActivity(new Intent(this, NetworkJavaActivity.class)); // 基本网络请求以及JSON解析
//            startActivity(new Intent(this, SensorJavaActivity.class));  // 传感器的使用
//            startActivity(new Intent(this, SensorDirectionJavaActivity.class));   // 方向传感器的使用
//            startActivity(new Intent(this, RotateAnimationJavaActivity.class)); // RotateAnimation动画
//            startActivity(new Intent(this, TouchEventJavaActivity.class)); Activity的事件分发逻辑
//            startActivity(new Intent(this, ViewGroupActivity.class));   // ViewGroup的事件分发逻辑
//            startActivity(new Intent(this, ViewActivity.class));    // View的事件分发逻辑
//              startActivity(new Intent(this, ViewScrollJavaActivity.class));    // View的滑动
//            startActivity(new Intent(this, AnimationJavaActivity.class));   // View的动画
//            startActivity(new Intent(this, ChangeLayoutParamsJavaActivity.class));  // 修改View的LayoutParams
//            startActivity(new Intent(this, ScrollerJavaActivity.class));    // Scroller
//            startActivity(new Intent(this, ScrollConflictJavaActivity.class));  // 滑动冲突问题
//            startActivity(new Intent(this, LinkRecycleViewJavaActivity.class));   // 二级RV
//            startActivity(new Intent(this, LayoutInflaterJavaActivity.class));    // LayoutInflater的使用
//            startActivity(new Intent(this, CustomViewJavaActivity.class));  // 自定义View
//            startActivity(new Intent(this, CanvasCustomViewJavaActivity.class));    // Canvas 自定义onDraw
//            startActivity(new Intent(this, ViewStatusJavaActivity.class));  // View Status
//            startActivity(new Intent(this, LruCacheJavaActivity.class));    // LruCache算法实现测试
//            startActivity(new Intent(this, MvcActivity.class)); // MVC 架构模式
//            startActivity(new Intent(this, MvpActivity.class)); // MVP架构模式
//            startActivity(new Intent(this, MvvmActivity.class));    // MVVM架构模式
//            startActivity(new Intent(this, RxJavaActivity.class));  // RxJava
//            startActivity(new Intent(this, OkHttpActivity.class));  // Okhttp
//            startActivity(new Intent(this, RetrofitActivity.class));    // Retrofit
//            startActivity(new Intent(this, GlideActivity.class));   // Glide
//            startActivity(new Intent(this, GlideCustomTargetActivity.class));   // Glide custom target
            startActivity(new Intent(this, Glide4Activity.class));  // Glide4.0用法
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