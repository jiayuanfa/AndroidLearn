package com.example.androidlearn.network.glide;

import android.annotation.SuppressLint;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * 自定义Glide API
 */
@GlideExtension
public class MyGlideExtension {

    private MyGlideExtension () {

    }

    @SuppressLint("CheckResult")
    @GlideOption
    public static void cacheSource(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
    }
}
