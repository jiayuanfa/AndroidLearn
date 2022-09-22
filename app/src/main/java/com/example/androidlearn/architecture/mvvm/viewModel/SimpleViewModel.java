package com.example.androidlearn.architecture.mvvm.viewModel;

import com.example.androidlearn.architecture.mvc.callback.Callback1;
import com.example.androidlearn.architecture.mvvm.http.HttpUtilsForMvvm;
import com.example.androidlearn.architecture.mvvm.model.UserInfo;
import com.example.androidlearn.databinding.ActivityMvvmBinding;

public class SimpleViewModel extends AbstractViewModel<ActivityMvvmBinding>{

    public SimpleViewModel(ActivityMvvmBinding activityMvvmBinding) {
        super(activityMvvmBinding);
    }

    /**
     * 提供该外接获取Model的方法
     * @param uid
     * @param callback1
     */
    public void getUserInfo(String uid, Callback1<UserInfo> callback1) {
        callback1.onCallBack(new HttpUtilsForMvvm().getUserInfo(uid));
    }
}
