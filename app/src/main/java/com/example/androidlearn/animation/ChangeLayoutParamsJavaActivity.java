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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.androidlearn.R;

/**
 * LayoutParams
 * 代码修改View的LayoutParams
 */
public class ChangeLayoutParamsJavaActivity extends AppCompatActivity {

    private Button myButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);

        myButton = findViewById(R.id.mButton);

        myButton.setOnClickListener(view -> {
            changeLayoutParams();
        });
    }

    private void changeLayoutParams() {
        ConstraintLayout.LayoutParams clp = (ConstraintLayout.LayoutParams) myButton.getLayoutParams();
        clp.width += 100;
        clp.height += 100;
        clp.setMarginStart(clp.getMarginStart() + 50);
        clp.topMargin += 50;
        myButton.setLayoutParams(clp);    // 此方法能修改到所有参数
//        myButton.requestLayout();   // 此方法不会修改Margin
    }
}
