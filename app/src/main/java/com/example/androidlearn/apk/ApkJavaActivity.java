package com.example.androidlearn.apk;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * 程序打包APK的步骤
 * META-INF\（注：Jar文件中常可以看到）
 * res\（注：存放资源文件的目录）
 * AndroidManifest.xml（注：程序全局配置文件）
 * classes.dex（注：Dalvik字节码）
 * resources.arsc（注：编译后的二进制资源文件）
 *
 * 1：Generate Signed APK
 * 2: 创建一个签名文件
 * 3：打包
 */
public class ApkJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }
}
