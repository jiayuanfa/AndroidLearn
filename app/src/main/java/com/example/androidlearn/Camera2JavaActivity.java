package com.example.androidlearn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.utils.PermissionsUtil;
import com.example.androidlearn.utils.Utils;
import com.example.androidlearn.widget.Camera2TextureView;

import java.util.Arrays;

/**
 * Camera2相机预览
 */
public class Camera2JavaActivity extends AppCompatActivity {

    private static final String TAG = Camera2JavaActivity.class.getSimpleName();
    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT; // 要打开的摄像头ID
    private CameraCharacteristics mCameraCharacteristics; // 相机属性
    public CameraManager mCameraManager; // 相机管理者
    private CameraDevice mCameraDevice; // 相机对象
    private CameraCaptureSession mCaptureSession;   // 捕捉对话
    private CaptureRequest.Builder mPreviewRequestBuilder; // 相机预览请求的构造器
    private ImageReader mImageReader;   // 预览图片读取
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private OrientationEventListener mOrientationEventListener;

    private Size mPreviewSize; // 预览大小
    private Size mPictureSize; // 拍照大小
    private int mDisplayRotation = 0; // 原始Sensor画面顺时针旋转该角度后，画面朝上
    private int mDeviceOrientation = 0; // 设备方向，由相机传感器获取

    // 缩放相关
    private final int MAX_ZOOM = 200; // 放大的最大值，用于计算每次放大/缩小操作改变的大小
    private int mZoom = 0; // 0~mMaxZoom之间变化
    private float mStepWidth; // 每次改变的宽度大小
    private float mStepHeight; // 每次改变的高度大小

    // 预览线程
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    private Camera2TextureView mCamera2TextureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        mCamera2TextureView = findViewById(R.id.mTextureView);

        findViewById(R.id.mGoResultPageBtn).setOnClickListener(view -> {
            startActivity(new Intent(this, EmptyJavaActivity.class));
        });
    }

    @Override
    protected void onResume() {

        /**
         *  1: 获取相机权限
         */
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
     * 2:
     * 2：设置SurfaceTextureListener
     * 2.1: 拿到mSurfaceTexture
     * 3: 拿到摄像头ID
     * 4: 求得最佳预览自适应、预览参数、3A
     * 5：打开相机
     * 6: 启动预览线程
     * 7：创建捕捉会话
     * 8：启动预览请求
     * 9：启动获取图片的线程
     * 10：拿到预览帧图片
     */
    private void cameraLaunchPreview() {
        mOrientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                mDeviceOrientation = orientation;
            }
        };
        setSurfaceTextureListener();
    }

    /**
     * 2：设置SurfaceTextureListener
     * 2.1: 拿到mSurfaceTexture
     */
    private void setSurfaceTextureListener() {
        if (mCamera2TextureView.isAvailable()) {
            mSurfaceTexture = mCamera2TextureView.getSurfaceTexture();
        } else {
            mCamera2TextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

                @Override
                public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
                    mSurfaceTexture = surfaceTexture;

                    /**
                     * 3: 打开相机
                     */
                    openCamera();
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
     * 3: 打开相机
     * 3.1：获取CameraManager
     * 3.2: 启动相机线程
     */
    @SuppressLint("MissingPermission")
    private void openCamera() {

        mOrientationEventListener.enable();
        try {

            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            startBackgroundThread();

            mCameraCharacteristics = mCameraManager.getCameraCharacteristics(Integer.toString(mCameraId));
            StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] supportPictureSizes = map.getOutputSizes(ImageFormat.JPEG);
            Size pictureSize = new Size(1920, 1080);
            float aspectRatio = pictureSize.getHeight() * 1.0f / pictureSize.getWidth();
            Size[] supportPreviewSizes = map.getOutputSizes(SurfaceTexture.class);

            // 求得预览参数
            mPreviewSize = Utils.chooseOptimalSize(supportPreviewSizes,
                    mCamera2TextureView.getWidth(),
                    mCamera2TextureView.getHeight());
            mCamera2TextureView.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            mPictureSize = pictureSize;
            // 每次切换摄像头计算一次就行，结果缓存到成员变量中
            initDisplayRotation();
            initZoomParameter();

            mCameraManager.openCamera(Integer.toString(mCameraId), new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    mCameraDevice = cameraDevice;
                    createCommonSession();
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                    releaseCamera();
                }

                @Override
                public void onError(@NonNull CameraDevice cameraDevice, int i) {
                    releaseCamera();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * createCommonSession
     * 9: 创建ImageReader 用来捕捉预览图片
     */
    private void createCommonSession() {
        mImageReader = ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1);
//        mImageReader.setOnImageAvailableListener(mActivity.mImageAvailableListener, mBackgroundHandler);

        // 设置预览参数

        mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        mSurface = new Surface(mSurfaceTexture);
        try {
            // 一个session中，所有CaptureRequest能够添加的target，必须是outputs的子集，所以在创建session的时候需要都添加进来
            mCameraDevice.createCaptureSession(
                    Arrays.asList(mSurface, mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            mCaptureSession = session;
                            startPreview();
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                            Log.e(TAG, "ConfigureFailed. session: " + session);
                        }

                        @Override
                        public void onClosed(@NonNull CameraCaptureSession session) {
                            super.onClosed(session);
                        }
                    }, null); // handle 传入 null 表示使用当前线程的 Looper
        } catch (CameraAccessException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * startPreview
     * 11: 构建预览捕捉请求 createCaptureRequest
     * 12：把预览的 Surface 以及 捕捉图片的 ImageReaderSurface设置进去
     * 13: 开始预览，即一直发送预览的请求
     */
    public void startPreview() {
        Log.v(TAG, "startPreview");
        if (mCameraDevice == null || mCaptureSession == null) {
            Log.w(TAG, "startPreview: mCaptureSession or mPreviewRequestBuilder is null");
            return;
        }

        if (mSurface == null) {
            Log.e(TAG, "initPreviewRequest failed, mPreviewSurface is null");
            return;
        }
        try {
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(mSurface); // 设置预览输出的 Surface
//            mPreviewRequestBuilder.addTarget(mImageReader.getSurface()); // 设置图片回调的 Surface
            // 打开3A总开关
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
            // 设置连续自动对焦
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 设置自动曝光
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_MODE_AUTO);
            // 设置自动白平衡
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, CaptureRequest.CONTROL_AWB_MODE_AUTO);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        try {
            // 开始预览，即一直发送预览的请求
            CaptureRequest captureRequest = mPreviewRequestBuilder.build();
            mCaptureSession.setRepeatingRequest(captureRequest, null, null);
            mCamera2TextureView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handleZoom(true);
                }
            }, 2000);
        } catch (CameraAccessException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化缩放参数
     */
    private void initZoomParameter() {
        Rect rect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        Log.d(TAG, "sensor_info_active_array_size: " + rect);
        // max_digital_zoom 表示 active_rect 除以 crop_rect 的最大值
        float max_digital_zoom = mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
        Log.d(TAG, "max_digital_zoom: " + max_digital_zoom);
        // crop_rect的最小宽高
        float minWidth = rect.width() / max_digital_zoom;
        float minHeight = rect.height() / max_digital_zoom;
        // 因为缩放时两边都要变化，所以要除以2
        mStepWidth = (rect.width() - minWidth) / MAX_ZOOM / 2;
        mStepHeight = (rect.height() - minHeight) / MAX_ZOOM / 2;
    }

    /**
     * 摄像头方向回正
     */
    private void initDisplayRotation() {
        int displayRotation = getWindowManager().getDefaultDisplay().getRotation();
        switch (displayRotation) {
            case Surface.ROTATION_0:
                displayRotation = 90;
                break;
            case Surface.ROTATION_90:
                displayRotation = 0;
                break;
            case Surface.ROTATION_180:
                displayRotation = 270;
                break;
            case Surface.ROTATION_270:
                displayRotation = 180;
                break;
        }
        int sensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        mDisplayRotation = (displayRotation + sensorOrientation + 270) % 360;
        Log.d(TAG, "mDisplayRotation: " + mDisplayRotation);
    }

    /**
     * 放大缩小并预览
     *
     * @param isZoomIn
     */
    public void handleZoom(boolean isZoomIn) {
        if (mCameraDevice == null || mCameraCharacteristics == null || mPreviewRequestBuilder == null) {
            return;
        }
        if (isZoomIn) {
            mZoom = 100;
        } else {
            mZoom = 0;
        }
        Log.v(TAG, "handleZoom: mZoom: " + mZoom);
        Rect rect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        int cropW = (int) (mStepWidth * mZoom);
        int cropH = (int) (mStepHeight * mZoom);
        Rect zoomRect = new Rect(rect.left + cropW, rect.top + cropH, rect.right - cropW, rect.bottom - cropH);
        Log.d(TAG, "zoomRect: " + zoomRect);
        mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoomRect);

        // 需要重新 start preview 才能生效
        try {
            // 开始预览，即一直发送预览的请求
            CaptureRequest captureRequest = mPreviewRequestBuilder.build();
            mCaptureSession.setRepeatingRequest(captureRequest, null, null);
        } catch (CameraAccessException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        if (mBackgroundThread == null || mBackgroundHandler == null) {
            Log.v(TAG, "startBackgroundThread");
            mBackgroundThread = new HandlerThread("CameraBackground");
            mBackgroundThread.start();
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        }
    }

    private void stopBackgroundThread() {
        Log.v(TAG, "stopBackgroundThread");
        if (mBackgroundThread != null) {
            mBackgroundThread.quitSafely();
            try {
                mBackgroundThread.join();
                mBackgroundThread = null;
                mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * releaseCamera
     */
    public void releaseCamera() {
        Log.v(TAG, "releaseCamera");

        stopBackgroundThread();

        if (null != mCaptureSession) {
            mCaptureSession.close();
            mCaptureSession = null;
        }
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        if (mImageReader != null) {
            mImageReader.close();
            mImageReader = null;
        }
        mOrientationEventListener.disable();
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
