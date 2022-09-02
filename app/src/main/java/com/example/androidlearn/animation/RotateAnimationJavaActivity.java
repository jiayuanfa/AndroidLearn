package com.example.androidlearn.animation;

import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * RotateAnimation的使用
 */
public class RotateAnimationJavaActivity extends AppCompatActivity {

    private String TAG = RotateAnimationJavaActivity.class.getSimpleName();
    private ImageView compassView;
    private int degree = 20;
    private int lastDegree = 0;
    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rotate_animation);

        compassView = findViewById(R.id.mIv);

        startAnimationThread();
    }

    private void startAnimationThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rotateAnimation();
                }
            }
        }).start();
    }

    private void rotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                lastDegree,
                degree,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation.setDuration(1000);
//        rotateAnimation.setRepeatMode(RotateAnimation.RESTART);
//        rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
        rotateAnimation.setFillAfter(true);
        compassView.startAnimation(rotateAnimation);
        lastDegree = degree;
        degree += 20;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
