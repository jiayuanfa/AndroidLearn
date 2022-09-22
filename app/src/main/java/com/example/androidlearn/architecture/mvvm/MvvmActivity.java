package com.example.androidlearn.architecture.mvvm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.androidlearn.R;
import com.example.androidlearn.architecture.mvc.callback.Callback1;
import com.example.androidlearn.architecture.mvvm.model.UserInfo;
import com.example.androidlearn.architecture.mvvm.viewModel.SimpleViewModel;
import com.example.androidlearn.databinding.ActivityMvvmBinding;

/**
 * 架构模式
 * MVVM
 * MVP中我们说过随着业务逻辑的增加，UI的改变多的情况下，会有非常多的跟UI相关的case，
 * 这样就会造成View的接口会很庞大。
 * 而MVVM就解决了这个问题，通过双向绑定的机制，
 * 实现数据和UI内容，只要想改其中一方，另一方都能够及时更新的一种设计理念，
 * 这样就省去了很多在View层中写很多case的情况，只需要改变数据就行
 *
 * MVP中View和presenter要相互持有，方便调用对方
 * MVVM中 View和ViewModel通过Binding进行关联，他们之前的关联处理通过DataBinding完成
 *
 * Q: MVVM与DataBinding的关系
 * A: 一句话表述就是，MVVM是一种思想，DataBinding是谷歌推出的方便实现MVVM的工具。
 *    在google推出DataBinding之前，因为xml layout功能较弱，想实现MVVM非常困难。
 *    而DataBinding的出现可以让我们很方便的实现MVVM
 *
 *
 */
public class MvvmActivity extends AppCompatActivity {

    private SimpleViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMvvmBinding activityMvvmBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_mvvm);
        mViewModel = new SimpleViewModel(activityMvvmBinding);
        setDataToView();

        activityMvvmBinding.mGetUserInfoButton.setOnClickListener(view -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setAge(18);
            userInfo.setName("发哥");
            mViewModel.mViewDataBinding.setUser(userInfo);
        });
    }

    private void setDataToView() {
        mViewModel.getUserInfo("1", new Callback1<UserInfo>() {
            @Override
            public void onCallBack(UserInfo userInfo) {
                mViewModel.mViewDataBinding.setUser(userInfo);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }
}
