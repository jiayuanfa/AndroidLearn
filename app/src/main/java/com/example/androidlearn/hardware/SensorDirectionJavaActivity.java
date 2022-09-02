package com.example.androidlearn.hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

import java.util.Arrays;

/**
 * Sensor 方向传感器的使用
 *
 * 谷歌的建议做法
 * 首先实例化两个传感器，加速度传感accelerometer器和地磁场传感器magnetic，然后在监听器代码里获取相对应的数组值，代入getRotationMatrix()方法得到R数组的值，再将R数组的值传入getOrientation()方法得到我们所需要的values数组
 * 作者：Moon_Bin
 * 链接：https://juejin.cn/post/6844904083585761288
 * 来源：稀土掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class SensorDirectionJavaActivity extends AppCompatActivity {

    private String TAG = SensorDirectionJavaActivity.class.getSimpleName();

    private TextView contentTextView;

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;

    private Sensor accelerometer;//加速度传感器
    private Sensor magnetic;//地磁场传感器
    private ImageView compassView;  // 指南针View

    //getRotationMatrix()方法需要的gravity参数
    private float[] accelerometerValues=new float[3];
    //getRotationMatrix()方法需要的geomagnetic参数
    private float[] magneticValues=new float[3];

    private float predegree = 0;

    private long time = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor_direction);

        compassView = findViewById(R.id.mIv);

        // 获取管理器
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        // 初始化加速度传感器
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(@NonNull SensorEvent sensorEvent) {
                System.out.println(TAG + Arrays.toString(sensorEvent.values));

                // 拿到加速器传感器的值
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    accelerometerValues = sensorEvent.values;
                }

                // 拿到地磁场传感器的值
                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    magneticValues = sensorEvent.values;
                }

                // 用于接收Z轴(方向轴)、XY轴(旋转轴)的值
                float[] values = new float[3];

                // 用于接收getRotationMatrix的值
                float[] R = new float[9];
                SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
                SensorManager.getOrientation(R, values);

                // 拿到方向角度
                float degree = (float) Math.toDegrees(values[0]);

                System.out.println("原始旋转角度为：" + degree);

                // 老的TYPE_ORIENTATION角度范围是[0, 360]，而谷歌推荐的这套新的API拿到的角度是[-180，180], 所以要做处理
                if (degree < 0) {
                    degree = (360 - (degree + (float) 360.0));
                } else {
                    degree = 360 - degree;
                }

                // 根据角度进行旋转动画
                RotateAnimation rotateAnimation = new RotateAnimation(
                        predegree,
                        degree,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                );
                rotateAnimation.setDuration(1000);
                rotateAnimation.setFillAfter(true);

                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                    compassView.startAnimation(rotateAnimation);
//                    System.out.println("旋转角度为：" + degree);
                    predegree = degree;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        // 注册监听器
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }
}
