package com.example.androidlearn.architecture.mvp.http;

import com.example.androidlearn.architecture.mvc.model.UserInfo;

public class HttpUtilsForMvp {
    public UserInfo getUserInfo(String uid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(30);
        userInfo.setName("贾元发");
        return userInfo;
    }
}
