package com.example.androidlearn.network.retrofit;

import com.example.androidlearn.network.model.Translation;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 步骤3：创建 用于描述网络请求 的接口
 */
public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
}
