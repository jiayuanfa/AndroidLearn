package com.example.androidlearn.ui.customView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.androidlearn.R;

/**
 * 自定义顶部视图
 */
public class NavigationBarView extends ConstraintLayout {

    private Button leftButton;

    private TextView titleText;

    public NavigationBarView(@NonNull Context context) {
        this(context, null);
    }

    public NavigationBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载视图
        LayoutInflater.from(context).inflate(R.layout.view_navigation_bar, this);

        leftButton = findViewById(R.id.mBackButton);
        titleText = findViewById(R.id.mTitleTv);

        leftButton.setOnClickListener(view -> {
            ((Activity) getContext()).finish();
        });
    }


    public void setTitleText(String text) {
        titleText.setText(text);
    }

    public void setLeftButtonText(String text) {
        leftButton.setText(text);
    }

    public void setLeftButtonListener(OnClickListener l) {
        leftButton.setOnClickListener(l);
    }
}
