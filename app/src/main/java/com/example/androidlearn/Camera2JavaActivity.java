package com.example.androidlearn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.camera2.AutoTextureView;
import com.example.androidlearn.camera2.Camera2Proxy;
import com.example.androidlearn.utils.PermissionsUtil;
import com.example.androidlearn.camera2.Camera2TextureView;

/**
 * Camera2相机预览
 */
public class Camera2JavaActivity extends AppCompatActivity {

    private Camera2TextureView mCamera2TextureView;
    private AutoTextureView mAutoTextureView;
    private Camera2Proxy mCamera2Proxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        mCamera2TextureView = findViewById(R.id.mTextureView);
        mAutoTextureView = findViewById(R.id.mTextureView2);

        findViewById(R.id.mGoResultPageBtn).setOnClickListener(view -> {
            startActivity(new Intent(this, EmptyJavaActivity.class));
        });
    }

    @Override
    protected void onResume() {

        if (PermissionsUtil.hasPermissions(
                this,
                PermissionsUtil.GROUP_CAMERA)) {
            cameraLaunchPreview();
        } else {
            checkPermission();
        }
        super.onResume();
    }

    /**
     * 在自定义个TextureView中启动相机
     * 会有变形的问题
     */
    private void cameraLaunchPreview() {
//        mCamera2TextureView.setVisibility(View.VISIBLE);
//        mAutoTextureView.setVisibility(View.GONE);
//        mCamera2Proxy = mCamera2TextureView.getCameraProxy();

        cameraLaunchPreview2();
    }

    /**
     * 不在自定义的TextureView中启动相机
     * 不会有变形的问题
     */
    private void cameraLaunchPreview2() {

        mCamera2TextureView.setVisibility(View.GONE);
        mAutoTextureView.setVisibility(View.VISIBLE);

        mCamera2Proxy = new Camera2Proxy(this);

        if (mAutoTextureView.isAvailable()) {
            launchCamera(
                    mAutoTextureView.getSurfaceTexture(),
                    mAutoTextureView.getWidth(),
                    mAutoTextureView.getHeight());

        } else {
            mAutoTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
                    launchCamera(surfaceTexture, width, height);
                }

                @Override
                public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

                }
            });
        }
    }

    /**
     * 启动相机
     * @param surfaceTexture
     * @param width
     * @param height
     */
    private void launchCamera(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
        mCamera2Proxy.setUpCameraOutputs(width, height);
        mCamera2Proxy.setTextureView(surfaceTexture);
        mCamera2Proxy.setTextureView(mAutoTextureView);

        // 设置预览自适应
        int previewWidth = mCamera2Proxy.getPreviewSize().getWidth();
        int previewHeight = mCamera2Proxy.getPreviewSize().getHeight();
        mAutoTextureView.setAspectRatio(previewHeight, previewWidth);

        mCamera2Proxy.openCamera();
    }

    /**
     * 获取相机权限
     */
    private void checkPermission() {
        PermissionsUtil.getInstance().requestPermissions(
                this,
                PermissionsUtil.GROUP_CAMERA,
                new PermissionsUtil.OnPermissionsListener() {
                    @Override
                    public void onPermissionGranted(int requestCode) {
                        cameraLaunchPreview();
                    }

                    @Override
                    public void onPermissionShowRationale(
                            int requestCode,
                            @NonNull String[] permissions) {
                        new AlertDialog.Builder(getBaseContext())
                                .setTitle("权限申请")
                                .setMessage("允许使用相机")
                                .setPositiveButton("确认", (dialogInterface, i) -> {
                                    checkPermission(); //如果想继续同意权限 就重新调用改方法
                                }).setNegativeButton("取消", null)
                                .create().show();
                    }

                    @Override
                    public void onPermissionDenied(int requestCode) {
                        new AlertDialog.Builder(getBaseContext())
                                .setTitle("权限设置")
                                .setMessage("去权限设置")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        PermissionsUtil.startAppSettingForResult(this);
                                    }
                                }).setNegativeButton("取消", null)
                                .create().show();
                    }
                }
        );
    }
}
