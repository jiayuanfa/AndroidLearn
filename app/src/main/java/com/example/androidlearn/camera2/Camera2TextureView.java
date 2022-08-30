package com.example.androidlearn.camera2;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

/**
 * 集成了mCameraProxy相机代理逻辑的类
 */
public class Camera2TextureView extends TextureView {

    private static final String TAG = "CameraTextureView";

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;
    private Camera2Proxy mCameraProxy;

    public Camera2TextureView(Context context) {
        this(context, null);
    }

    public Camera2TextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Camera2TextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Camera2TextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setSurfaceTextureListener(mSurfaceTextureListener);
        mCameraProxy = new Camera2Proxy((Activity) context);
    }

    public Camera2Proxy getCameraProxy() {
        return mCameraProxy;
    }

    private SurfaceTextureListener mSurfaceTextureListener = new SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.v(TAG, "onSurfaceTextureAvailable. width: " + width + ", height: " + height);
            mCameraProxy.setUpCameraOutputs(width, height);
            mCameraProxy.setTextureView(surface);
            mCameraProxy.setTextureView(getRootView());
            mCameraProxy.openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            Log.v(TAG, "onSurfaceTextureSizeChanged. width: " + width + ", height: " + height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            Log.v(TAG, "onSurfaceTextureDestroyed");
            mCameraProxy.releaseCamera();
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

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
