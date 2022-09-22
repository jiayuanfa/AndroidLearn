package com.example.androidlearn.architecture.mvc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;
import com.example.androidlearn.architecture.mvc.callback.Callback1;
import com.example.androidlearn.architecture.mvc.model.SimpleModel;
import com.example.androidlearn.architecture.mvc.model.UserInfo;

/**
 * 架构模式
 * MVC
 */
public class MvcActivity extends AppCompatActivity {

    private TextView mNameTv;
    private TextView mAgeTv;
    private SimpleModel simpleModel = new SimpleModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mvc);


        mNameTv = findViewById(R.id.mNameTv);
        mAgeTv = findViewById(R.id.mAgeTv);
        findViewById(R.id.mGetUserInfoButton).setOnClickListener(view -> {
            simpleModel.getUserInfo("1", new Callback1<UserInfo>() {
                @Override
                public void onCallBack(UserInfo userInfo) {
                    mNameTv.setText(userInfo.getName());
                    mAgeTv.setText(String.valueOf(userInfo.getAge()));
                }
            });
        });
    }
}
