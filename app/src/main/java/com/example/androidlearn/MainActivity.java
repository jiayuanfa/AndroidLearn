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
 * Handler?????? ?????????java??????
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * ?????????????????????Message?????????Handler???????????????????????????????????????
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
//            startActivity(new Intent(this, Camera2JavaActivity.class));   // ??????
//            startActivity(new Intent(this, SharePreferencesJavaActivity.class));    // SP
//            startActivity(new Intent(this, NetworkJavaActivity.class)); // ????????????????????????JSON??????
//            startActivity(new Intent(this, SensorJavaActivity.class));  // ??????????????????
//            startActivity(new Intent(this, SensorDirectionJavaActivity.class));   // ????????????????????????
//            startActivity(new Intent(this, RotateAnimationJavaActivity.class)); // RotateAnimation??????
//            startActivity(new Intent(this, TouchEventJavaActivity.class)); Activity?????????????????????
//            startActivity(new Intent(this, ViewGroupActivity.class));   // ViewGroup?????????????????????
//            startActivity(new Intent(this, ViewActivity.class));    // View?????????????????????
//              startActivity(new Intent(this, ViewScrollJavaActivity.class));    // View?????????
//            startActivity(new Intent(this, AnimationJavaActivity.class));   // View?????????
//            startActivity(new Intent(this, ChangeLayoutParamsJavaActivity.class));  // ??????View???LayoutParams
//            startActivity(new Intent(this, ScrollerJavaActivity.class));    // Scroller
//            startActivity(new Intent(this, ScrollConflictJavaActivity.class));  // ??????????????????
//            startActivity(new Intent(this, LinkRecycleViewJavaActivity.class));   // ??????RV
//            startActivity(new Intent(this, LayoutInflaterJavaActivity.class));    // LayoutInflater?????????
//            startActivity(new Intent(this, CustomViewJavaActivity.class));  // ?????????View
//            startActivity(new Intent(this, CanvasCustomViewJavaActivity.class));    // Canvas ?????????onDraw
//            startActivity(new Intent(this, ViewStatusJavaActivity.class));  // View Status
//            startActivity(new Intent(this, LruCacheJavaActivity.class));    // LruCache??????????????????
//            startActivity(new Intent(this, MvcActivity.class)); // MVC ????????????
//            startActivity(new Intent(this, MvpActivity.class)); // MVP????????????
//            startActivity(new Intent(this, MvvmActivity.class));    // MVVM????????????
//            startActivity(new Intent(this, RxJavaActivity.class));  // RxJava
//            startActivity(new Intent(this, OkHttpActivity.class));  // Okhttp
//            startActivity(new Intent(this, RetrofitActivity.class));    // Retrofit
//            startActivity(new Intent(this, GlideActivity.class));   // Glide
//            startActivity(new Intent(this, GlideCustomTargetActivity.class));   // Glide custom target
            startActivity(new Intent(this, Glide4Activity.class));  // Glide4.0??????
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
                    message.obj = "????????????????????????";
                    myHandler.sendMessage(message);
                    time--;
                }
            }
        }.start();
    }

    /**
     * ?????????????????????????????????Handler?????????????????????????????????
     */
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // ??????????????????UI??????
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