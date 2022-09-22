package com.example.androidlearn.architecture.mvvm.http;

import com.example.androidlearn.architecture.mvvm.model.UserInfo;

public class HttpUtilsForMvvm {
    public UserInfo getUserInfo(String uid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(30);
        userInfo.setName("贾元发");
        return userInfo;
    }
}
