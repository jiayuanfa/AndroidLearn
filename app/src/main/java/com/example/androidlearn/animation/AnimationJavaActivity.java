package com.example.androidlearn.animation;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * Animation
 *
 * 补间动画：
 * 补间动画 实现的平移 实际上只是对View的影像做操作，并不会真正改变View的位置参数。
 * 如果我们添加 android:fillAfter="true" 的话，当动画结束后，则会停在最后的位置。
 * 此时你会发现一个问题，当我们再次点击View时，并不会触发动画效果，但是点击之前的位置则会触发。
 *
 * 属性动画：
 * 属性动画 则只有点到View所在的位置才会触发长按事件。
 */
public class AnimationJavaActivity extends AppCompatActivity {

    private Button myButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);

        myButton = findViewById(R.id.mButton);

        myButton.setOnClickListener(view -> {
            @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(this, R.xml.animation_translate);
            myButton.startAnimation(animation);
        });

        myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ObjectAnimator
                        .ofFloat(myButton, "translationX", 0, 400)
                        .setDuration(2000)
                        .start();
                return true;
            }
        });
    }
}
