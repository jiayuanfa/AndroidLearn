package com.example.androidlearn.hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

import java.util.Arrays;

/**
 * Sensor 距离传感器的使用
 */
public class SensorJavaActivity extends AppCompatActivity {

    private String TAG = SensorJavaActivity.class.getSimpleName();

    private TextView contentTextView;

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(@NonNull SensorEvent sensorEvent) {
            System.out.println(TAG + Arrays.toString(sensorEvent.values));
            if (sensorEvent.values[0] == 0.0) {
                contentTextView.setText("宝宝去睡觉吧！");
            } else {
                contentTextView.setText("宝宝起来玩耍吧！");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor);

        contentTextView = findViewById(R.id.mTv);

        // 获取管理器
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        // 获取距离传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
}
