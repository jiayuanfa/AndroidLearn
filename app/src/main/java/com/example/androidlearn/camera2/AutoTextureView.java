package com.example.androidlearn.camera2;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

/**
 * 自适应的TextureView
 * 不含任何相机初始化逻辑
 */
public class AutoTextureView extends TextureView {

    private static final String TAG = "CameraTextureView";

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    public AutoTextureView(Context context) {
        this(context, null);
    }

    public AutoTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AutoTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = 0;
        int resultHeight = 0;

        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width > height * mRatioWidth / mRatioHeight) {
                resultWidth = width;
                resultHeight = width * mRatioHeight / mRatioWidth;
            } else {
                resultWidth = height * mRatioWidth / mRatioHeight;
                resultHeight = height;
            }
            setMeasuredDimension(resultWidth, resultHeight);
        }

        System.out.println("onMeasure:" + "预览宽度为:" + mRatioWidth + "预览高度为:" + mRatioHeight);
        System.out.println("onMeasure:" + "宽度为:" + resultWidth + "高度为:" + resultHeight);
    }
}
