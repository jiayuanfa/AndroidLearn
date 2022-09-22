package com.example.androidlearn.architecture.mvp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;
import com.example.androidlearn.architecture.mvc.callback.Callback1;
import com.example.androidlearn.architecture.mvc.model.SimpleModel;
import com.example.androidlearn.architecture.mvc.model.UserInfo;
import com.example.androidlearn.architecture.mvp.contract.SimpleContract;

/**
 * 架构模式
 * MVP
 * 通过引入接口BaseView，让相应的视图组件如Activity，Fragment去实现BaseView，
 * 实现了视图层的独立，通过中间层Preseter实现了Model和View的完全解耦。
 * MVP彻底解决了MVC中View和Controller傻傻分不清楚的问题，
 * 但是随着业务逻辑的增加，一个页面可能会非常复杂，UI的改变是非常多，会有非常多的case，
 * 这样就会造成View的接口会很庞大。
 *
 */
public class MvpActivity extends AppCompatActivity implements SimpleContract.View {

    private SimpleContract.Presenter mPresenter;

    private TextView mNameTv;
    private TextView mAgeTv;
    private SimpleModel simpleModel = new SimpleModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mvp);

        setPresenter(new SimpleContract.Presenter());

        mNameTv = findViewById(R.id.mNameTv);
        mAgeTv = findViewById(R.id.mAgeTv);
        findViewById(R.id.mGetUserInfoButton).setOnClickListener(view -> {
            mPresenter.getUserInfo("1", new Callback1<UserInfo>() {
                @Override
                public void onCallBack(UserInfo userInfo) {
                    setDataToView(userInfo);
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void setPresenter(SimpleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setDataToView(UserInfo userInfo) {
        mNameTv.setText(userInfo.getName());
        mAgeTv.setText(String.valueOf(userInfo.getAge()));
    }
}
