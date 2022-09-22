package com.example.androidlearn.architecture.mvc.model;

import com.example.androidlearn.architecture.mvc.callback.Callback1;
import com.example.androidlearn.architecture.mvc.http.HttpUtils;

public class SimpleModel implements BaseModel{

    private HttpUtils httpUtils = new HttpUtils();

    /**
     * 提供该外接获取Model的方法
     * @param uid
     * @param callback1
     */
    public void getUserInfo(String uid, Callback1<UserInfo> callback1) {
        callback1.onCallBack(httpUtils.getUserInfo(uid));
    }

    @Override
    public void onDestroy() {

    }
}
